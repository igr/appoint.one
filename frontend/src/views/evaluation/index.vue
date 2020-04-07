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
      <v-form>
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
            required
          />
          <v-textarea
            v-model="form.problem"
            label="Vrsta problema"
            required
          />
          <v-textarea
            v-model="form.help"
            label="Pružena intervencija"
            required
          />
          <v-combobox
            v-model="form.success"
            :items="[{ text:'DA', value: 1 }, { text:'NE', value: 0 }]"
            label="Da li je intervencija bila dovoljna?"
            outlined
            required
          />
          <v-combobox
            v-model="form.forward"
            :items="[{ text:'DA', value: 1 }, { text:'NE', value: 0 }]"
            label="Da li je korisnik upućen dalje?"
            outlined
            required
          />
          <v-textarea
            v-model="form.comment"
            label="Intervencija bila dovoljna / korisnik upućen dalje - komentar"
            required
          />
          <v-btn
            type="submit"
            color="primary"
            x-large
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

  async save() {
    await EvaluationApi.postNewEvaluation(this.id, this.form);
    await this.$router.push('/my');
  }
}
</script>

<style lang="scss">
</style>
