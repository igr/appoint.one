<template>
  <v-row justify="center">
    <v-col
      cols="8"
    >
      <v-card
        class="mx-auto"
        tile
      >
        <v-toolbar
          color="accent-4"
          dark
        >
          <v-toolbar-title>Sledeći termini:</v-toolbar-title>
        </v-toolbar>
        <v-list
          nav
          two-line
        >
          <v-list-item-group
            v-model="selected"
            active-class="pink--text"
          >
            <v-list-item
              v-for="item in timeslotAndDoctorsList"
              :key="item.title"
              :set="doc = item.doctor"
            >
              <v-list-item-content>
                <v-list-item-title>
                  {{ toDateString(item.timeslot.datetime) }}
                </v-list-item-title>
                <v-list-item-subtitle>
                  <div>{{ doc.data.name }}, {{ occupationText(doc.data.occupation) }}</div>
                </v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-icon>
                <v-icon>chat_bubble</v-icon>
              </v-list-item-icon>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-card>
      <v-row
        justify="center"
        class="mt-6"
      >
        <v-btn
          color="primary"
          class="ma-auto"
          :disabled="!isSelected"
          @click="submit"
        >
          Izaberi i potvrdi termin
        </v-btn>
      </v-row>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import TimeslotApi from '@/api/TimeslotApi';
import AppCookies from '@/utils/cookies';
import { TimeslotAndDoctor } from '@/model/Timeslot';
import { DateTime } from '@/model/DateTime';
import { isStatus } from '@/utils/http';
import { AppModule } from '@/store/modules/app';
import { toDateTimeHumanString } from '@/utils/time';
import { occupationOf } from '@/utils/data';

@Component
export default class AvailableTimeslots extends Vue {
  private timeslotAndDoctorsList: Array<TimeslotAndDoctor> = [];

  private selected = -1;

  get isSelected() {
    return this.selected !== undefined && this.selected > -1;
  }

  toDateString(datetime: DateTime) {
    return toDateTimeHumanString(datetime);
  }

  occupationText(value: number) {
    return occupationOf(value);
  }

  created() {
    this.fetchData();
  }

  private async fetchData() {
    const country = AppCookies.getCountry() || '1';
    const city = AppCookies.getCity() || '1';
    const { data } = await TimeslotApi.listNextTimeslots(country, city);
    this.timeslotAndDoctorsList = data;
  }

  async submit() {
    if (!this.isSelected) {
      return;
    }
    const timeslotId = this.timeslotAndDoctorsList[this.selected].timeslot.id;

    try {
      await TimeslotApi.reserveTimeslot(timeslotId);
      await this.$router.push(`/appointment/${timeslotId}`);
    } catch (err) {
      if (isStatus(err.response, 409)) {
        AppModule.setInfo({ message: 'Ne može da se rezerviše.', type: 'error' });
      }
    }
  }
}
</script>

<style>
</style>
