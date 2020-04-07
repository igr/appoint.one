<template>
  <v-row
    align="center"
    justify="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <h1 class="heading">
        Evaluacija
      </h1>
      <v-form v-model="valid">
        <v-card
          class="pa-6"
        >
          <v-radio-group
            v-model="form.sex"
            label="Pol"
            row
            required
          >
            <v-radio
              label="Muški"
              :value="1"
            />
            <v-radio
              label="Ženski"
              :value="0"
            />
            <v-radio
              label="Drugo"
              :value="2"
            />
          </v-radio-group>
          <v-text-field
            v-model="form.age"
            label="Uzrast korisnika (Broj godina)"
            type="number"
            :rules="rules.age"
            required
          />
          <v-textarea
            v-model="form.problem"
            label="Vrsta problema"
            :rules="rules.problem"
            required
          />
          <v-textarea
            v-model="form.help"
            label="Pružena intervencija"
            :rules="rules.help"
            required
          />
          <v-combobox
            v-model="form.success"
            :items="[{ text:'DA', value: 1 }, { text:'NE', value: 0 }]"
            label="Da li je intervencija bila dovoljna?"
            outlined
            :rules="rules.success"
            required
          />
          <v-combobox
            v-model="form.forward"
            :items="[{ text:'DA', value: 1 }, { text:'NE', value: 0 }]"
            label="Da li je korisnik upućen dalje?"
            outlined
            :rules="rules.forward"
            required
          />
          <v-textarea
            v-model="form.comment"
            label="Intervencija bila dovoljna / korisnik upućen dalje - komentar"
            :rules="rules.comment"
            required
          />
          <v-btn
            type="submit"
            color="primary"
            x-large
            :disabled="!valid"
            @click.stop="save"
          >
            SNIMI
          </v-btn>
        </v-card>
      </v-form>
      <router-link to="/my">
        NAZAD
      </router-link>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import { NewEvaluation } from '@/model/NewEvaluation';
import EvaluationApi from '@/api/EvaluationApi';

@Component({
  name: 'Evaluation',
})
export default class extends Vue {
  private form: NewEvaluation = {
    sex: 2,
  } as NewEvaluation;

  @Prop({ default: -1 })
  readonly id!: number;

  private valid = false;

  private rules = {
    age: [
      (v: number) => !!v || 'Godine obavezne',
      (v: number) => (v > 0 && v < 200) || 'Godine moraju biti validne (1 - 200)',
    ],
    problem: [
      (v: string) => !!v || 'Vrsta problema obavezna',
    ],
    help: [
      (v: string) => !!v || 'Pruzena intervencija obavezna',
    ],
    success: [
      (v: string) => !!v || 'Polje obavezno',
    ],
    forward: [
      (v: string) => !!v || 'Polje obavezno',
    ],
    comment: [
      (v: string) => !!v || 'Komentar obavezan',
    ],

  };


  async save() {
    await EvaluationApi.postNewEvaluation(this.id, this.form);
    await this.$router.push('/my');
  }
}
</script>

<style lang="scss">
</style>
