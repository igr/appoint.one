import {
  VuexModule, Module, Mutation, Action, getModule,
} from 'vuex-module-decorators';
import store from '@/store';
import { City } from '@/model/City';
import CitiesApi from '@/api/CitiesApi';
import { Country } from '@/model/Country';

export interface GeoCacheState {
  cities: City[];
}

@Module({ dynamic: true, store, name: 'geo' })
class GeoCacheClass extends VuexModule implements GeoCacheState {
  cities: City[] = [];

  countries: Country[] = [];

  @Mutation
  private SET_CITIES(cities: City[]) {
    this.cities = cities;
  }

  @Action
  public async GetCities() {
    if (this.cities.length === 0) {
      const res = await CitiesApi.cities();

      this.context.commit('SET_CITIES', res && res.data);
    }
    return this.cities;
  }

  @Mutation
  private SET_COUNTRIES(countries: Country[]) {
    this.countries = countries;
  }

  @Action
  public async GetCountries() {
    if (this.countries.length === 0) {
      const res = await CitiesApi.countries();
      this.context.commit('SET_COUNTRIES', res && res.data);
    }

    return this.countries;
  }
}

export const GeoCacheModule = getModule(GeoCacheClass);
