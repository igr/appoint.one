<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <doctor-profile :doc="doctor" />

      <v-row justify="center">
        <v-btn
          dark
          large
          class="col-12 mt-6 col-md-6"
          color="primary"
          to="/my/newtime"
        >
          <v-icon>mdi-plus</v-icon> Dodavanje termina
        </v-btn>
      </v-row>
      <v-divider class="ma-12" />
      <v-row
        :v-if="isLoading"
        class="mt-12"
        align="center"
        justify="center"
      >
        <v-col
          cols="12"
          md="6"
        >
          <h3>Prvi sledeći termini:</h3>
          <v-timeline dense>
            <v-slide-x-reverse-transition
              group
              hide-on-leave
            >
              <v-timeline-item
                v-for="item in future"
                :key="item.id"
                :color="colorOf(item)"
                small
                fill-dot
              >
                <div class="next">
                  {{ dateStr(item.datetime) }}
                </div>
              </v-timeline-item>
            </v-slide-x-reverse-transition>
          </v-timeline>
        </v-col>
      </v-row>
      <!-- CALENDAR -->
      <v-sheet height="64">
        <v-toolbar
          flat
        >
          <v-btn
            outlined
            class="mr-4"
            color="grey darken-2"
            @click="setToday"
          >
            Danas
          </v-btn>
          <v-btn
            fab
            text
            small
            color="grey darken-2"
            @click="prev"
          >
            <v-icon small>
              mdi-chevron-left
            </v-icon>
          </v-btn>
          <v-btn
            fab
            text
            small
            color="grey darken-2"
            @click="next"
          >
            <v-icon small>
              mdi-chevron-right
            </v-icon>
          </v-btn>
          <v-spacer />
          <v-menu
            bottom
            right
          >
            <template>
              <v-btn
                outlined
                color="grey darken-2"
              >
                <span>{{ typeToLabel[type] }}</span>
                <v-icon right>
                  mdi-menu-down
                </v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item @click="type = 'day'">
                <v-list-item-title>Dan</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'week'">
                <v-list-item-title>Nedelja</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'month'">
                <v-list-item-title>Mesec</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = '4day'">
                <v-list-item-title>4 dana</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-toolbar>
      </v-sheet>
      <v-sheet
        height="600"
        class="mb-12"
      >
        <v-calendar
          ref="calendar"
          v-model="focus"
          color="primary"
          locale="rs"
          :events="events"
          :event-name="eventName"
          :event-color="getEventColor"
          :first-interval="6"
          :interval-count="24-6"
          :now="today"
          :type="type"
          @click:event="showEvent"
        />
      </v-sheet>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { UserModule } from '@/store/modules/user';
import { Doctor } from '@/model/Doctor';
import DoctorApi from '@/api/DoctorApi';
import {
  isTimeslotCanceled, isTimeslotDone, isTimeslotReserved, Timeslot,
} from '@/model/Timeslot';
import {
  isInFuture, toDateTimeHumanString, toDateTimeString,
} from '@/utils/time';
import DoctorProfile from '@/components/DoctorProfile/index.vue';
import { DateTime } from '@/model/DateTime';

@Component({
  name: 'My',
  components: {
    DoctorProfile,
  },
})
export default class extends Vue {
  private dialog2 = {
    show: false,
    text: '',
    action: -1,
    item: {} as Timeslot,
  };

  private isLoading = true;

  private timeslots: Timeslot[] = [];

  get doctor(): Doctor {
    return UserModule.doctor;
  }

  get future(): Timeslot[] {
    return this.timeslots
      .filter((it) => isInFuture(it.datetime))
      .slice(0, 4)
      .reverse();
  }

  dateStr(datetime: DateTime): string {
    return toDateTimeHumanString(datetime);
  }

  colorOf(item: Timeslot): string {
    if (this.isRESERVED(item)) {
      return 'red';
    }
    if (this.isDONE(item)) {
      return 'green';
    }
    if (this.isCANCELED(item)) {
      return 'black';
    }
    return 'blue';
  }

  async created() {
    this.isLoading = true;
    await this.fetchData();
  }

  private async fetchData() {
    const res = await DoctorApi.getDoctorTimeslots(this.doctor.id);
    this.timeslots = res.data;
    this.isLoading = false;
  }

  private isRESERVED = isTimeslotReserved;

  private isDONE = isTimeslotDone;

  private isCANCELED = isTimeslotCanceled;

  private type = '4day';

  private typeToLabel = {
    month: 'Mesec',
    week: 'Nedelja',
    day: 'Dan',
    '4day': '4 Dana',
  };

  private focus = '';

  private today = new Date().toISOString().slice(0, 10);

  setToday() {
    this.focus = this.today;
  }

  get events() {
    return this.timeslots.map((ts) => ({
      name: '*',
      start: toDateTimeString(ts.datetime),
      end: this.formatDatePlus30(ts.datetime),
      color: 'red',
      id: ts.id,
      item: ts,
    }));
  }

  getEventColor(event: any) {
    if (this.isRESERVED(event.item)) {
      return 'red';
    }
    if (this.isDONE(event.item)) {
      return 'green';
    }
    if (this.isCANCELED(event.item)) {
      return 'black';
    }
    return 'blue';
  }

  private get calendarInstance(): Vue & {
    prev: () => void;
    next: () => void;
    getFormatter: (format: any) => any;
    } {
    return this.$refs.calendar as Vue & {
      prev: () => void;
      next: () => void;
      getFormatter: (format: any) => any;
    };
  }

  eventName(event: any) {
    return `${event.start.hour}:${event.start.minute}`;
  }

  prev() {
    this.calendarInstance.prev();
  }

  next() {
    this.calendarInstance.next();
  }

  formatDatePlus30(dt: DateTime) {
    const date = new Date(dt.year, dt.month - 1, dt.day, dt.hour, dt.minute);
    const a = new Date(date.getTime() + 30 * 60 * 1000);
    return a.toISOString().slice(0, 10);
  }

  // eslint-disable-next-line no-unused-vars
  async showEvent({ nativeEvent, event }: any) {
    await this.$router.push(`/my/appointment/${event.id}`);
  }
}
</script>

<style lang="scss" scoped>
.next {
  font-size: 2em;
  font-weight: bold;
}
</style>
