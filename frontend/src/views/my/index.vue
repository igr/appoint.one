import {TimeslotStatus} from "@/model/Timeslot";
import {TimeslotStatus} from "@/model/Timeslot";
<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog2.show"
      max-width="290"
    >
      <v-card>
        <v-card-title class="headline">
          Potvrdite
        </v-card-title>

        <v-card-text>
          {{ dialog2.text }}
        </v-card-text>

        <v-card-actions>
          <v-spacer />

          <v-btn
            color="green darken-1"
            text
            @click="dialog2.show = false"
          >
            NE
          </v-btn>

          <v-btn
            color="green darken-1"
            text
            @click="dialogOk()"
          >
            DA
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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

      <v-divider class="ma-12" />

      <v-row
        :v-if="isLoading"
        class="mt-12"
      >
        <v-col cols="6">
          <h3>Budući termini</h3>
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
                  v-if="isNEW(item)"
                  fab
                  x-small
                  class="ml-6"
                  @click.stop="removeTimeslot0(item)"
                >
                  <v-icon>mdi-minus</v-icon>
                </v-btn>
              </v-timeline-item>
            </v-slide-x-reverse-transition>
          </v-timeline>
        </v-col>
        <v-col cols="6">
          <h3>Prošli termini</h3>
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
                  v-if="isNEW(item)"
                  fab
                  x-small
                  class="ml-6"
                  @click.stop="removeTimeslot0(item)"
                >
                  <v-icon>mdi-minus</v-icon>
                </v-btn>
                <v-btn
                  v-if="isRESERVED(item)"
                  fab
                  x-small
                  class="ml-6"
                  @click.stop="cancelTimeslot0(item)"
                >
                  <v-icon>mdi-cancel</v-icon>
                </v-btn>
                <v-btn
                  v-if="isRESERVED(item)"
                  fab
                  x-small
                  class="ml-6"
                  @click.stop="doneTimeslot(item)"
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
import {
  isTimeslotCanceled,
  // eslint-disable-next-line no-unused-vars
  isTimeslotDone, isTimeslotNew, isTimeslotReserved, Timeslot, TimeslotStatus,
} from '@/model/Timeslot';
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
  private dialog2 = {
    show: false,
    text: '',
    action: -1,
    item: {} as Timeslot,
  };

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

  cancel() {
    // this.$refs.form.reset();
    (this.$refs.form as Vue & { reset: () => void }).reset();
    this.dialog = false;
    this.dialog2.show = false;
  }

  async submit() {
    const res = await TimeslotApi.post(this.doctor.id, toDateTime(this.date, this.time));
    const allTimeslots: Timeslot[] = res.data;

    allTimeslots.forEach((ts) => this.timeslots.push(ts));
    this.cancel();
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

  dialogOk() {
    const { action } = this.dialog2;
    this.dialog2.show = false;
    switch (action) {
      case 1: this.removeTimeslot(this.dialog2.item); break;
      case 2: this.cancelTimeslot(this.dialog2.item); break;
      default: throw Error('BAD action');
    }
  }

  async removeTimeslot0(timeslot: Timeslot) {
    this.dialog2.item = timeslot;
    this.dialog2.text = 'Da li želite da obrište termin?';
    this.dialog2.action = 1;
    this.dialog2.show = true;
  }

  async removeTimeslot(timeslot: Timeslot) {
    await TimeslotApi.delete(timeslot.id);
    const index = this.timeslots.indexOf(timeslot);
    if (index > -1) {
      this.timeslots.splice(index, 1);
    }
  }

  async cancelTimeslot0(timeslot: Timeslot) {
    this.dialog2.item = timeslot;
    this.dialog2.text = 'Da li želite da označite termin kao OTKAZAN?';
    this.dialog2.action = 2;
    this.dialog2.show = true;
  }

  async cancelTimeslot(timeslot: Timeslot) {
    await TimeslotApi.cancelTimeslot(timeslot.id);
    // eslint-disable-next-line no-param-reassign
    timeslot.status = TimeslotStatus.CANCELED;
  }

  private isNEW = isTimeslotNew;

  private isRESERVED = isTimeslotReserved;

  private isDONE = isTimeslotDone;

  private isCANCELED = isTimeslotCanceled;

  doneTimeslot(timeslot: Timeslot) {
    this.$router.push(`/evaluation/${timeslot.id}`);
  }
}
</script>

<style lang="scss" scoped></style>
