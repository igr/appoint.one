<template>
  <v-row
    justify="center"
    align="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <v-card
        class="mx-auto"
        tile
      >
        <v-toolbar
          color="accent-4"
          dark
        >
          <v-toolbar-title>
            Sledeći termini ({{ dateAsString }})
          </v-toolbar-title>
          <v-spacer />
          <v-menu
            v-model="calmenu"
            :close-on-content-click="false"
            transition="scale-transition"
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-btn
                icon
                @click="calmenu = true"
                v-on="on"
              >
                <v-icon>
                  mdi-calendar
                </v-icon>
              </v-btn>
            </template>
            <v-date-picker
              v-model="date"
              no-title
              scrollable
              @input="calmenu = false"
            />
          </v-menu>
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
              v-for="(item, index) in timeslotAndDoctorsList"
              :key="item.timeslot.id"
            >
              <v-list-item-content>
                <v-list-item-title
                  class="pt-4 pb-4"
                >
                  <span
                    class="font-weight-bold green white--text pa-2"
                  >
                    {{ toDateOnlyHumanString(item.timeslot.datetime) }}
                    {{ toTimeOnlyHumanString(item.timeslot.datetime) }}
                  </span>
                </v-list-item-title>
                <v-list-item-subtitle>
                  {{ item.doctor.data.name }}, {{ occupationText(item.doctor) }}
                </v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-icon>
                <v-btn
                  v-if="index === selected"
                  color="primary"
                  class="mr-2 z-50"
                  :disabled="!isSelected"
                  @click.stop="submit"
                >
                  Potvrdi
                </v-btn>
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
          x-large
          :disabled="!isSelected"
          @click="submit"
        >
          <h5 v-if="isSelected">
            Potvrdi termin
          </h5>
          <h5 v-if="!isSelected">
            Izaberi termin
          </h5>
        </v-btn>
      </v-row>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Watch } from 'vue-property-decorator';
import TimeslotApi from '@/api/TimeslotApi';
import { TimeslotAndDoctor } from '@/model/Timeslot';
import { DateTime } from '@/model/DateTime';
import { isStatus } from '@/utils/http';
import { AppModule } from '@/store';
import {
  toDateString, toDateTime, toTimeString,
} from '@/utils/time';
import { occupationOf } from '@/utils/data';
import { Doctor } from '@/model/Doctor';
import App from '@/App.vue';

@Component
export default class AvailableTimeslots extends App {
  private timeslotAndDoctorsList: Array<TimeslotAndDoctor> = [];

  private selected = -1;

  private calmenu = false;

  private date = new Date().toISOString().substr(0, 10);

  @Watch('date')
  async onPropertyChanged(value: string) {
    this.selected = -1;
    const { data } = await TimeslotApi.listNextTimeslots(value);
    this.timeslotAndDoctorsList = data;
  }

  get isSelected() {
    return this.selected !== undefined && this.selected > -1;
  }

  toDateOnlyHumanString(datetime: DateTime) {
    return toDateString(datetime);
  }

  toTimeOnlyHumanString(datetime: DateTime) {
    return toTimeString(datetime);
  }

  get dateAsString() {
    const dt = toDateTime(this.date, '00:00');
    return toDateString(dt);
  }

  occupationText(doc: Doctor) {
    return occupationOf(doc, true);
  }

  created() {
    this.fetchData();
  }

  private async fetchData() {
    const { data } = await TimeslotApi.listNextTimeslots(this.date);
    this.timeslotAndDoctorsList = data;
  }

  async submit() {
    if (!this.isSelected) {
      return;
    }
    const confirm = await this.$root.$confirm('Potvrdi termin', 'Ovom akcijom potvrđujete izabrani termin za razgovor sa izabranim terapeutom. Da li ste sigurni?');
    if (confirm) {
      const timeslotId = this.timeslotAndDoctorsList[this.selected].timeslot.id;
      try {
        const res = await TimeslotApi.reserveTimeslot(timeslotId);
        if (res.data === 0) {
          const refresh = await this.$root.$confirm('Izabrani termin je već zauzet', 'Osvežite stranicu za nove podatke.', { confirmText: 'Osveži' });
          if (refresh) {
            this.fetchData();
          }
        } else {
          await this.$router.push(`/appointment/${timeslotId}`);
        }
      } catch (err) {
        if (isStatus(err.response, 409)) {
          AppModule.setAlert({ message: 'Rezervacija nije uspela.', type: 'error' });
        }
      }
    }
  }
}
</script>

<style lang="scss">
span.date {
  font-weight: bold;
}
span.time {
  font-size: 2em;
}
</style>
