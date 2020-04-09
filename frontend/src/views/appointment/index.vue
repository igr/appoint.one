<template>
  <v-row
    justify="center"
    align="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <h1 class="heading text-center">
        ZAKAZANI TERMIN #{{ id }}
      </h1>
      <div v-if="!isLoading">
        <day-big :datetime="timeslot && timeslot.datetime" />

        <v-row justify="center">
          <v-btn
            dark
            x-large
            class="ma-2 col-10 col-md-4"
            @click="downloadIcs"
          >
            <v-icon
              dark
              class="mr-2"
            >
              mdi-calendar-month
            </v-icon>
            dodaj u kalendar
          </v-btn>
        </v-row>
        <v-row justify="center">
          <v-btn
            dark
            x-large
            :href="calendarUrl"
            class="ma-2 col-10 col-md-4"
            target="_blank"
          >
            <v-icon
              dark
              class="mr-2"
            >
              mdi-google
            </v-icon>
            GUGL kalendar
          </v-btn>
        </v-row>

        <doctor-profile :doc="doctor" />
        <p class="text-center text-uppercase mt-6">
          Sačuvajte link do ove stranice i podatke.<br>
          U naznačeno vreme pozovite psihoterapeuta.<br>
        </p>
      </div>
      <div v-else>
        {{ $t('msg.pleaseWait') }}
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import { Timeslot } from '@/model/Timeslot';
import { Doctor } from '@/model/Doctor';
import DoctorProfile from '@/components/DoctorProfile/index.vue';
import DayBig from '@/components/DayBig/index.vue';
import AppoitmentApi from '@/api/AppoitmentApi';
import { DateTime } from '@/model/DateTime';
import { occupationOf } from '@/utils/data';

@Component({
  name: 'Appointment',
  components: {
    DoctorProfile,
    DayBig,
  },
})
export default class extends Vue {
  @Prop({ default: -1 })
  readonly id!: number;

  private doctor: Doctor | undefined;

  private timeslot: Timeslot | undefined;

  private isLoading = true;

  get calendarUrl() {
    if (!this.doctor || !this.timeslot) return null;
    const data = {
      title: this.encode('Termin kod psihoterapeuta'),
      details: this.encode(`${this.doctor.data.name} - ${occupationOf(this.doctor.data.occupation)} e-mail: ${this.doctor.data.email} Tel. +381${this.doctor.data.phone}`),
      location: this.encode(`http://podrskapsihoterapeuta.com/appointment/${this.id}`),
      dates: this.formatDateTime(this.timeslot.datetime),
    };
    return `https://www.google.com/calendar/render?action=TEMPLATE&text=${data.title}&details=${data.details}&location=${data.location}&dates=${data.dates}`;
  }

  async created() {
    this.isLoading = true;
    await this.fetchData();
  }

  private async fetchData() {
    const { data } = await AppoitmentApi.get(this.id);
    this.doctor = data.doctor;
    this.timeslot = data.timeslot;
    this.isLoading = false;
  }

  async downloadIcs() {
    await AppoitmentApi.downloadIcal(this.id);
  }

  private formatDateTime(d: DateTime) {
    const start = new Date(d.year, d.month - 1, d.day, d.hour, d.minute);
    const end = new Date(start);
    end.setMinutes(start.getMinutes() + 30);
    const startFmt = start.toISOString().replace(/-|:|\.\d\d\d/g, '');
    const endFmt = end.toISOString().replace(/-|:|\.\d\d\d/g, '');
    return `${startFmt}/${endFmt}`;
  }

  private encode(txt: string): string {
    return encodeURIComponent(txt).replace(/%20/g, '+');
  }
}
</script>

<style scoped>
</style>
