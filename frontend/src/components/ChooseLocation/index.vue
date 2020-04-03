<template>
  <div>
    <v-row no-gutters>
      <v-col
        cols="3"
        class="offset-2"
      >
        <div>Država</div>
        <v-menu
          v-if="componentCreated"
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
// import CitiesApi from '@/api/CitiesApi';
import { GeoCacheModule } from '@/store/modules/geo-cache';

  @Component
export default class ChooseLocation extends Vue {
    private countries: Country[] = [];

    private allCities: City[] = [];

    private cities: City[] = [];

    private countryIndex = -1;

    private cityIndex = 0;

    private selectedCity = null;

    private componentCreated = false;

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
      }
    }

    async beforeCreate() {
      this.componentCreated = false;
      this.allCities = await GeoCacheModule.GetCities();
      this.countries = await GeoCacheModule.GetCountries();
      this.countryClicked(0);
      this.componentCreated = true;
    }

    img() {
      return '@/assets/images/country-1.png';
    }
}
</script>

<style>
</style>
