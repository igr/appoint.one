<template>
  <v-row justify="center">
    <v-col
      cols="12"
    >
      <v-card>
        <v-card-title>
          Pregled evaluacija
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
          :items="evals"
          :items-per-page="30"
          :search="search"
          class="elevation-1"
        >
          <template v-slot:item.data.success="{ item }">
            <v-simple-checkbox v-model="item.data.success" disabled></v-simple-checkbox>
          </template>
          <template v-slot:item.data.forward="{ item }">
            <v-simple-checkbox v-model="item.data.forward" disabled></v-simple-checkbox>
          </template>
        </v-data-table>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import EvaluationApi from '@/api/EvaluationApi';
import { Evaluation } from '@/model/Evaluation';

@Component({
  name: 'AdminEvals',
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
    { text: 'Termin', value: 'timeslotId' },
    { text: 'Pol', value: 'data.sex' },
    { text: 'Godine', value: 'data.age' },
    { text: 'Problem', value: 'data.problem' },
    { text: 'Pomoć', value: 'data.help' },
    { text: 'Uspešno', value: 'data.success' },
    { text: 'Dalje', value: 'data.forward' },
    { text: 'Komentar', value: 'data.comment' },
  ];

  private evaluations: Evaluation[] = [];

  get evals(): Evaluation[] {
    return this.evaluations;
  }

  async created() {
    await this.fetchData();
  }

  private async fetchData() {
    const { data } = await EvaluationApi.getAll();
    const d: Evaluation[] = data;
    d.forEach((it) => this.evaluations.push(it));
  }
}
</script>

<style scoped>
</style>
