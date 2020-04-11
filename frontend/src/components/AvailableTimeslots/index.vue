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
                  <div>{{ doc.data.name }}, {{ occupationText(doc) }}</div>
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
          <h5 v-if="isSelected">
            Potvrdi termin
          </h5>
          <h5 v-if="!isSelected">
            Izaberi termin
          </h5>
        </v-btn>
      </v-row>

      <v-dialog
        v-model="showDialog"
        max-width="290"
      >
        <v-card>
          <v-card-title>
            Izabrani termin je<br>već zauzet
          </v-card-title>
          <v-card-text>
            Osvežite stranicu za nove podatke.
          </v-card-text>

          <v-card-actions>
            <v-spacer />
            <v-btn
              color="green darken-1"
              text
              @click="showDialog = false"
            >
              OK
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import TimeslotApi from '@/api/TimeslotApi';
import { TimeslotAndDoctor } from '@/model/Timeslot';
import { DateTime } from '@/model/DateTime';
import { isStatus } from '@/utils/http';
import { AppModule } from '@/store';
import { toDateTimeHumanString } from '@/utils/time';
import { occupationOf } from '@/utils/data';
import { Doctor } from '@/model/Doctor';

@Component
export default class AvailableTimeslots extends Vue {
  private timeslotAndDoctorsList: Array<TimeslotAndDoctor> = [];

  private selected = -1;

  private showDialog = false;

  get isSelected() {
    return this.selected !== undefined && this.selected > -1;
  }

  toDateString(datetime: DateTime) {
    return toDateTimeHumanString(datetime);
  }

  occupationText(doc: Doctor) {
    return occupationOf(doc, true);
  }

  created() {
    this.fetchData();
  }

  private async fetchData() {
    const { data } = await TimeslotApi.listNextTimeslots();
    this.timeslotAndDoctorsList = data;
  }

  async submit() {
    if (!this.isSelected) {
      return;
    }
    const timeslotId = this.timeslotAndDoctorsList[this.selected].timeslot.id;

    try {
      const res = await TimeslotApi.reserveTimeslot(timeslotId);
      if (res.data === 0) {
        this.showDialog = true;
      } else {
        await this.$router.push(`/appointment/${timeslotId}`);
      }
    } catch (err) {
      if (isStatus(err.response, 409)) {
        AppModule.setInfo({ message: 'Rezervacija nije uspela.', type: 'error' });
      }
    }
  }
}
</script>

<style>
</style>
