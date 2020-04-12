<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <doctor-profile
        :doc="doctor"
        :public-view="false"
      />

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
                  <v-btn
                    icon
                    @click="showEvent({event: item})"
                  >
                    <v-icon>mdi-pencil</v-icon>
                  </v-btn>
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
            <template v-slot:activator="{ on }">
              <v-btn
                outlined
                color="grey darken-2"
                v-on="on"
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
import { UserModule } from '@/store';
import { Doctor } from '@/model/Doctor';
import DoctorApi from '@/api/DoctorApi';
import { Timeslot, TimeslotStatus } from '@/model/Timeslot';
import {
  isInFuture, toDateTimeHumanString, toDateTimeString, toTimeString,
} from '@/utils/time';
import DoctorProfile from '@/components/DoctorProfile/index.vue';
import { DateTime } from '@/model/DateTime';
import { CalendarEventParsed, CalendarEvent } from 'vuetify';

@Component({
  name: 'My',
  components: {
    DoctorProfile,
  },
})
export default class extends Vue {
  $refs!: {
    calendar: Vue & {
      prev: () => void;
      next: () => void;
    };
  }

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
    return this.timeslotStatusColor[item.status];
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

  private type = '4day';

  private typeToLabel = {
    month: 'Mesec',
    week: 'Nedelja',
    day: 'Dan',
    '4day': '4 Dana',
  };

  private timeslotStatusColor = {
    [TimeslotStatus.NEW]: 'blue',
    [TimeslotStatus.RESERVED]: 'red',
    [TimeslotStatus.CANCELED]: 'black',
    [TimeslotStatus.DONE]: 'green',
  };

  private focus = '';

  private today = new Date().toISOString().slice(0, 10);

  setToday() {
    this.focus = this.today;
  }

  get events() {
    return this.timeslots.map((ts) => ({
      name: TimeslotStatus[ts.status],
      start: toDateTimeString(ts.datetime),
      end: this.formatDatePlus30(ts.datetime),
      color: this.timeslotStatusColor[TimeslotStatus.RESERVED],
      id: ts.id,
      item: ts,
    }));
  }

  getEventColor({ item }: { item: Timeslot }) {
    return this.timeslotStatusColor[item.status];
  }

  eventName({ index, start }: CalendarEventParsed) {
    const event = this.events[index];
    return `${toTimeString(start)} ${event.name}`;
  }

  prev() {
    this.$refs.calendar.prev();
  }

  next() {
    this.$refs.calendar.next();
  }

  formatDatePlus30(dt: DateTime) {
    const start = new Date(dt.year, dt.month - 1, dt.day, dt.hour, dt.minute);
    const end = new Date(start);
    end.setMinutes(start.getMinutes() + 30);
    return end.toISOString().slice(0, 10);
  }

  showEvent({ event }: { event: CalendarEvent}) {
    this.$router.push(`/my/appointment/${event.id}`);
  }
}
</script>

<style lang="scss" scoped>
.next {
  font-size: 2em;
  font-weight: bold;
}
</style>
