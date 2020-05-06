<template>
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
    Google kalendar
  </v-btn>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import { Timeslot } from '@/model/Timeslot';
import { occupationOf } from '@/utils/data';
import { Doctor } from '@/model/Doctor';
import { DateTime } from '@/model/DateTime';

@Component
export default class CalendarGoogleButton extends Vue {
  @Prop() timeslot!: Timeslot;

  @Prop() doctor!: Doctor;

  get calendarUrl() {
    if (!this.doctor || !this.timeslot) return null;
    const data = {
      title: this.encode('Termin kod psihoterapeuta'),
      details: this.encode(`${this.doctor.data.name} - ${occupationOf(this.doctor, true)} e-mail: ${this.doctor.data.email} Tel. +381${this.doctor.data.phone}`),
      location: this.encode(`https://podrskapsihoterapeuta.com/appointment/${this.timeslot.id}`),
      dates: this.formatDateTime(this.timeslot.datetime),
    };
    return `https://www.google.com/calendar/render?action=TEMPLATE&text=${data.title}&details=${data.details}&location=${data.location}&dates=${data.dates}`;
  }

  private encode(txt: string): string {
    return encodeURIComponent(txt).replace(/%20/g, '+');
  }

  private formatDateTime(d: DateTime) {
    const start = new Date(d.year, d.month - 1, d.day, d.hour, d.minute);
    const end = new Date(start);
    end.setMinutes(start.getMinutes() + 30);
    const startFmt = start.toISOString().replace(/-|:|\.\d\d\d/g, '');
    const endFmt = end.toISOString().replace(/-|:|\.\d\d\d/g, '');
    return `${startFmt}/${endFmt}`;
  }
}
</script>
