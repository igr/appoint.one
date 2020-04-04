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
          class="col-6"
          color="primary"
          @click.stop="dialog = true"
        >
          <v-icon>mdi-plus</v-icon> Dodavanje termina
        </v-btn>
      </v-row>
      <v-dialog
        v-model="dialog"
        max-width="300"
      >
        <v-card class="pa-6">
          <v-card-title class="headline">
            Novi termin
          </v-card-title>
          <v-card-text />

          <v-form
            ref="form"
            v-model="valid"
          >
            <v-text-field
              v-model="date"
              label="Datum: YYYY/MM/DD"
              prepend-icon="event"
              :rules="rules.date"
            />
            <v-text-field
              v-model="time"
              label="Vreme: HH:MM"
              prepend-icon="event"
              :rules="rules.time"
            />

            <v-card-actions>
              <v-spacer />
              <v-btn
                color="gray"
                text
                @click="cancel"
              >
                Odustani
              </v-btn>
              <v-btn
                color="blue"
                text
                :disabled="!valid"
                @click="submit"
              >
                Dodaj
              </v-btn>
            </v-card-actions>
          </v-form>
        </v-card>
      </v-dialog>

      <v-row
        :v-if="isLoading"
        class="mt-12"
      >
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
                {{ dateStr(item.datetime) }}
                <v-btn
                  v-if="item.status === 'NEW'"
                  fab
                  x-small
                  alt="Obriši"
                  class="ml-6"
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
                {{ dateStr(item.datetime) }}

                <v-btn
                  fab
                  x-small
                  alt="Add comment ml-6"
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
import { isInFuture, toDateTime, toDateTimeString } from '@/utils/time';
import DoctorProfile from '@/components/DoctorProfile/index.vue';
// eslint-disable-next-line no-unused-vars
import { DateTime } from '@/model/DateTime';
import { isValidDate, isValidTime } from '@/utils/validate';

@Component({
  name: 'My',
  components: {
    DoctorProfile,
  },
})
export default class extends Vue {
  private isLoading = true;

  private valid = false;

  private timeslots: Timeslot[] = [];

  private dialog = false;

  private date = '';

  private time = '';

  private rules = {
    date: [
      (v: string) => !!v || 'Datum obavezan',
      (v: string) => isValidDate(v) || 'Datum mora biti validan',
      (v: string) => !!v && isValidDate(v),
    ],
    time: [
      (v: string) => !!v || 'Vreme obavezno',
      (v: string) => isValidTime(v) || 'Vreme mora biti validno',
      (v: string) => !!v && isValidTime(v),
    ],
  };

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

  dateStr(datetime: DateTime): String {
    return toDateTimeString(datetime);
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

  cancel() {
    this.$refs.form.reset();
    this.dialog = false;
  }

  async submit() {
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
