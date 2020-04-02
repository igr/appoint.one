<template>
  <v-row>
    <v-col
      cols="8"
      offset="2"
    >
      <v-card
        class="mx-auto mt-12"
        tile
      >
        <v-toolbar
          color="accent-4"
          dark
        >
          <v-toolbar-title>Prvi sledeći termini</v-toolbar-title>
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
              v-for="item in timeslotList"
              :key="item.title"
              :set="doc = item.doctor"
            >
              <v-list-item-content>
                <v-list-item-title>
                  {{ formatDate(item.datetime) }} {{ formatTime(item.datetime) }}
                </v-list-item-title>
                <v-list-item-subtitle>
                  <div>{{ doc.data.name }}, {{ doc.data.occupation }}</div>
                </v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-icon>
                <v-icon>chat_bubble</v-icon>
              </v-list-item-icon>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-card>
      <v-divider />
      <v-btn
        color="primary"
        @click="submit"
      >
        Potvrdi termin!
      </v-btn>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import TimeslotApi from '@/api/TimeslotApi';
import AppCookies from '@/utils/cookies';
// eslint-disable-next-line no-unused-vars
import { Timeslot } from '@/model/Timeslot';
// eslint-disable-next-line no-unused-vars
import { DateTime } from '@/model/DateTime';

@Component
export default class AvailableTimeslots extends Vue {
  private timeslotList: Array<Timeslot> = [];

  private selected = -1;

  formatDate(datetime: DateTime) {
    return `${datetime.year}-${datetime.month}-${datetime.day}`;
  }

  formatTime(datetime: DateTime) {
    return `${datetime.hour}:${datetime.minute}`;
  }

  created() {
    this.fetchData();
  }

  private async fetchData() {
    const country = AppCookies.getCountry() || '1';
    const city = AppCookies.getCity() || '1';
    const { data } = await TimeslotApi.listNextTimeslots(country, city);
    this.timeslotList = data;
  }

  submit() {
    if (this.selected === undefined) {
      return;
    }
    if (this.selected < 0) {
      return;
    }
    const timeslotId = this.timeslotList[this.selected].id.value;

    TimeslotApi.reserveTimeslot(timeslotId).then(
      (ok) => {
        if (!ok) {
          return;
        }
        this.$router.push('/appointment');
      },
    );
  }
}
</script>

<style>
</style>
