<template>
  <div>
    <v-row no-gutters>
      <v-col cols="3" class="offset-2">
        <div>Država</div>
        <v-menu
          :close-on-click="true"
          :close-on-content-click="true"
          :offset-x="false"
          :offset-y="true"
        >
          <template v-slot:activator="{ on }">
            <v-btn
              color="primary"
              dark
              v-on="on"
            >
              <img
                :src="require(`@/assets/images/country-${countries[countryIndex].id}.png`)"
                style="margin-right: 10px"
              >
              {{ countries[countryIndex].name }}
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(item, index) in countries"
              :key="index"
              @click="countryClicked(index)"
            >
              <v-list-item-title>
                <img
                  :src="require(`@/assets/images/country-${item.id}.png`)"
                  style="position: relative; top:10px;"
                >
                {{ item.name }}
              </v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-col>

      <v-col cols="4">
        <div>Grad</div>
        <v-autocomplete
          v-model="selectedCity"
          :items="cities.map(city => city.name)"
          dense
          solo
          placeholder="Type In"
          color="grey"
        />
      </v-col>
    </v-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
// eslint-disable-next-line no-unused-vars
import { Country } from '@/model/Country';
// eslint-disable-next-line no-unused-vars
import { City } from '@/model/City';
import CitiesApi from '@/api/CitiesApi';

  @Component
export default class ChooseLocation extends Vue {
    private countries: Country[] = [];

    private allCities: City[] = [];

    private cities: City[] = [];

    private countryIndex = -1;

    private cityIndex = 0;

    private selectedCity = null;

    private countryClicked(i: number) {
      if (i !== this.countryIndex) {
        this.countryIndex = i;
        this.cities = [];
        this.allCities.forEach((city) => {
          if (city.country === this.countries[this.countryIndex].id) {
            this.cities.push(city);
          }
        });
        this.cityIndex = 0;
        console.log(this.countries[this.countryIndex].name);
      }
    }

    async created() {
      this.allCities = [
        { id: 1, name: 'Beograd', country: 1 },
        { id: 2, name: 'Novi Sad', country: 1 },
        { id: 3, name: 'Zrenjanin', country: 1 },
        { id: 4, name: 'Pancevo', country: 1 },
        { id: 5, name: 'Cacak', country: 1 },
        { id: 6, name: 'Zagreb', country: 2 },
        { id: 7, name: 'Split', country: 2 },
        { id: 8, name: 'Rijeka', country: 2 },
        { id: 9, name: 'Dubrovnik', country: 2 },
        { id: 10, name: 'Pula', country: 2 },
        { id: 11, name: 'Sarajevo', country: 3 },
        { id: 12, name: 'Mostar', country: 3 },
        { id: 13, name: 'Tuzla', country: 3 },
        { id: 14, name: 'Bihac', country: 3 },
        { id: 15, name: 'Trebinje', country: 3 },
      ];
      this.countries = [
        { name: 'Srbija', id: 1 },
        { name: 'Bosnia', id: 2 },
        { name: 'Croatia', id: 3 },
      ];
      this.countryClicked(0);

      const co = await CitiesApi.countries();
      const ci = await CitiesApi.cities();
      this.allCities = ci.data;
      this.countries = co.data;
    }

    img() {
      return '@/assets/images/country-1.png';
    }
}
</script>

<style>
</style>
