<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <v-card>
        <v-card-title>
          Lista terapeuta
          <v-spacer />
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Pretraga"
            single-line
            hide-details
          />
        </v-card-title>
        <v-data-table
          :headers="headers"
          :items="doc"
          :items-per-page="15"
          :search="search"
          class="elevation-1"
        >
          <template v-slot:item.data.confirmed="{ item }">
            <v-checkbox
              v-model="item.data.confirmed"
              @change="updateDoctor(item.id, item.data.confirmed)"
            />
          </template>
        </v-data-table>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { Doctor } from '@/model/Doctor';
import DoctorApi from '@/api/DoctorApi';

@Component({
  name: 'AdminDoctors',
})
export default class extends Vue {
  private search = '';

  private headers = [
    {
      text: 'ID',
      align: 'start',
      sortable: true,
      value: 'id',
    },
    { text: 'Ime', value: 'data.name' },
    { text: 'e-pošta', value: 'data.email' },
    { text: 'Telefon', value: 'data.phone' },
    { text: 'Zoom', value: 'data.zoom' },
    { text: 'Potvrđen', value: 'data.confirmed' },
  ];

  updateDoctor(id: number, confirmed: boolean) {
    if (confirmed) {
      DoctorApi.confirmDoctor(id);
    } else {
      DoctorApi.deConfirmDoctor(id);
    }
  }

  private doctors: Doctor[] = [];

  get doc(): Doctor[] {
    return this.doctors;
  }

  async created() {
    this.fetchData();
  }

  private async fetchData() {
    const { data } = await DoctorApi.getAll();
    const d: Doctor[] = data;
    d.forEach((it) => this.doctors.push(it));
  }
}
</script>

<style scoped>
</style>
