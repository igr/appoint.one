import {
  VuexModule, Module, Mutation, Action, getModule,
} from 'vuex-module-decorators';
import store from '@/store';
import { City } from '@/model/City';
import CitiesApi from '@/api/CitiesApi';

export interface GeoCacheState {
  cities: City[];
}

@Module({ dynamic: true, store, name: 'geo' })
class GeoCacheClass extends VuexModule implements GeoCacheState {
  cities: City[] = [];

  @Mutation
  private SET_CITIES(cities: City[]) {
    this.cities = cities;
  }

  @Action
  public async GetCities() {
    if (this.cities.length === 0) {
      const res = await CitiesApi.cities();

      // Error happens here
      this.cities = res && res.data;
    }
    return this.cities;
  }
}

export const GetCacheModule = getModule(GeoCacheClass);
