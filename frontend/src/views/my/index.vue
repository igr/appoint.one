<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <h1 class="heading">
        {{ doctor.data.name }}
      </h1>
      <div class="subtitle-1">
        {{ doctor.data.occupation }}
      </div>
      <v-btn
        fab
        dark
        large
        color="primary"
        @click.stop="dialog = true"
      >
        <v-icon>mdi-plus</v-icon>
      </v-btn>
      <v-dialog
        v-model="dialog"
        max-width="300"
      >
        <v-card>
          <v-card-title class="headline">
            Novi termin
          </v-card-title>
          <v-card-text />

          <v-text-field
            v-model="date"
            label="Datum: YYYY/MM/DD"
            prepend-icon="event"
          />
          <v-text-field
            v-model="time"
            label="Vreme: HH:MM"
            prepend-icon="event"
          />

          <v-card-actions>
            <v-spacer />
            <v-btn
              color="gray"
              text
              @click="dialog = false"
            >
              Odustani
            </v-btn>
            <v-btn
              color="blue"
              text
              @click="submit"
            >
              Dodaj
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <h2 class="mt-6">
        Pregled termina
      </h2>

      <v-row :v-if="isLoading">
        <v-col cols="6">
          <h3>Budući</h3>
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
                {{ item.datetime.year }}/{{ item.datetime.month }}/{{ item.datetime.day }}
                {{ item.datetime.hour }}:{{ item.datetime.minute }}
                <v-btn
                  v-if="item.status === 'NEW'"
                  fab
                  x-small
                  alt="Obriši"
                  @click="removeTimeslot(item)"
                >
                  <v-icon>mdi-minus</v-icon>
                </v-btn>
              </v-timeline-item>
            </v-slide-x-reverse-transition>
          </v-timeline>
        </v-col>
        <v-col cols="6">
          <h3>Prošli</h3>
          <v-timeline
            dense
            col="6"
          >
            <v-slide-x-reverse-transition
              group
              hide-on-leave
            >
              <v-timeline-item
                v-for="item in past"
                :key="item.id"
                :color="colorOf(item)"
                small
                fill-dot
              >
                {{ item.datetime.year }}/{{ item.datetime.month }}/{{ item.datetime.day }}
                {{ item.datetime.hour }}:{{ item.datetime.minute }}
                <v-btn
                  fab
                  x-small
                  alt="Add comment"
                >
                  <v-icon>mdi-check</v-icon>
                </v-btn>
              </v-timeline-item>
            </v-slide-x-reverse-transition>
          </v-timeline>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { UserModule } from '@/store/modules/user';
// eslint-disable-next-line no-unused-vars
import { Doctor } from '@/model/Doctor';
import DoctorApi from '@/api/DoctorApi';
// eslint-disable-next-line no-unused-vars
import { Timeslot } from '@/model/Timeslot';
import TimeslotApi from '@/api/TimeslotApi';
import { isInFuture, toDateTime } from '@/utils/time';

@Component({
  name: 'My',
})
export default class extends Vue {
  private isLoading = true;

  private timeslots: Timeslot[] = [];

  private dialog = false;

  private date = '';

  private time = '';

  get doctor(): Doctor {
    return UserModule.doctor;
  }

  get future(): Timeslot[] {
    return this.timeslots
      .filter((it) => isInFuture(it.datetime))
      .reverse();
  }

  get past(): Timeslot[] {
    return this.timeslots
      .filter((it) => !isInFuture(it.datetime));
  }

  colorOf(item: {status: String}): string {
    if (item.status === 'RESERVED') {
      return 'red';
    }
    if (item.status === 'DONE') {
      return 'green';
    }
    return 'blue';
  }

  async submit() {
    console.log('Submit');
    const res = await TimeslotApi.post(this.doctor.id, toDateTime(this.date, this.time));
    const allTimeslots: Timeslot[] = res.data;

    allTimeslots.forEach((ts) => this.timeslots.push(ts));
    this.dialog = false;
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

  async removeTimeslot(timeslot: Timeslot) {
    await TimeslotApi.delete(timeslot.id);
    const index = this.timeslots.indexOf(timeslot);
    if (index > -1) {
      this.timeslots.splice(index, 1);
    }
  }
}
</script>

<style lang="scss" scoped></style>