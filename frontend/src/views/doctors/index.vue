<template>
  <v-card>
    <v-card-title>
      Lista terapeuta
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Pretraga"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="doctors"
      :items-per-page="15"
      :search="search"
      class="elevation-1"
    >
      <template v-slot:item.confirmed="{ item }">
        <v-simple-checkbox v-model="item.confirmed" disabled></v-simple-checkbox>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import { Component, Vue } from 'vue-property-decorator';

  @Component({
    name: 'ListOfDoctors',
  })
export default class extends Vue {
  data() {
    return {
      search: '',
      headers: [
        {
          text: 'ID',
          align: 'start',
          sortable: true,
          value: 'id',
        },
        { text: 'Ime', value: 'name' },
        { text: 'Prezime', value: 'email' },
        { text: 'Pol', value: 'sex' },
        { text: 'Drzava', value: 'country' },
        { text: 'Godina Rodjenja', value: 'year' },
        { text: 'Telefon', value: 'phone' },
        { text: 'Zoom', value: 'zoom' },
        { text: 'Potvrdjen', value: 'confirmed' },
        { text: 'Updatovan', value: 'dateUpdated' },
      ],
      doctors: [
        {
          id: 45,
          name: 'pera',
          email: 'pera@dot.com',
          sex: 'M',
          country: 'Serbia',
          year: 1990,
          phone: +38112344321,
          zoom: 'z o o m',
          confirmed: true,
          dateUpdated: '22-3-2019',
        },
        {
          id: 45,
          name: 'pera',
          email: 'pera@dot.com',
          sex: 'M',
          country: 'Serbia',
          year: 1990,
          phone: +38112344321,
          zoom: 'z o o m',
          confirmed: false,
          dateUpdated: '22-3-2019',
        },
        {
          id: 45,
          name: 'pera',
          email: 'pera@dot.com',
          sex: 'M',
          country: 'Serbia',
          year: 1990,
          phone: +38112344321,
          zoom: 'z o o m',
          confirmed: false,
          dateUpdated: '22-3-2019',
        },
      ],
    };
  }
}
</script>

<style scoped>
</style>
