<template>
  <v-row
    justify="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <h1 class="heading mb-6">
        Registracija
      </h1>

      <v-stepper
        v-model="step"
        :vertical="false"
      >
        <v-stepper-header>
          <v-stepper-step
            :complete="step > 1"
            step="1"
          >
            Podaci
          </v-stepper-step>
          <v-divider />
          <v-stepper-step
            :complete="step > 2"
            step="2"
          >
            Obrazovanje
          </v-stepper-step>
          <v-divider />
          <v-stepper-step step="3">
            Kontakt
          </v-stepper-step>
        </v-stepper-header>

        <v-stepper-items>
          <v-form
            ref="form"
            v-model="valid"
          >
            <!-- STEP 1 -->
            <v-stepper-content step="1">
              <v-text-field
                ref="nameRef"
                v-model="form.name"
                label="Ime i prezime"
                :rules="rules.nameAndSurname"
                prepend-icon="mdi-human"
                required
              />
              <v-text-field
                ref="emailRef"
                v-model="form.email"
                label="Email"
                type="email"
                prepend-icon="mdi-email"
                :rules="rules.email"
                required
              />
              <v-radio-group
                v-model="form.sex"
                :row="true"
                mandatory
                required
                label="Pol"
              >
                <v-radio
                  label="Muški"
                  value="true"
                />
                <v-radio
                  label="Ženski"
                  value="false"
                />
              </v-radio-group>
              <v-text-field
                ref="yearRef"
                v-model="form.year"
                label="Godina rođenja"
                type="number"
                prepend-icon="mdi-calendar"
                :rules="rules.year"
                required
              />

              <v-row
                justify="center"
                class="mt-6"
              >
                <v-btn
                  text
                  to="/home"
                >
                  Cancel
                </v-btn>
                <v-btn
                  v-if="showContinue"
                  color="primary"
                  @click="step = 2"
                >
                  Dalje
                </v-btn>
              </v-row>
            </v-stepper-content>

            <!-- STEP 2 -->
            <v-stepper-content step="2">
              <v-text-field
                ref="eduYearRef"
                v-model="form.education"
                label="Godina edukacije"
                type="number"
                :rules="rules.eduYear"
                required
              />
              <v-autocomplete
                v-model="form.occupation"
                :items="occupationItems"
                hide-no-data
                hide-selected
                label="Zanimanje"
              />
              <v-text-field
                v-if="isOccupationDrugo"
                ref="professionRef"
                v-model="form.occupation2"
                label="Zanimanje (uneti ručno)"
                :rules="rules.profession"
                required
              />
              <v-text-field
                v-if="isOccupationSpecial"
                ref="professionRef"
                v-model="form.occupationSpec"
                label="Vrsta specializacije"
                :rules="rules.profession"
                required
              />

              <v-radio-group
                v-model="form.certificate"
                label="Sertifikat"
                row
              >
                <v-radio
                  label="Ne"
                  :value="1"
                />
                <v-radio
                  label="Da - Domaći"
                  :value="2"
                />
                <v-radio
                  label="Da - Inostrani"
                  :value="3"
                />
              </v-radio-group>
              <v-autocomplete
                v-model="form.modalitet"
                :items="modalitetItems"
                hide-no-data
                hide-selected
                label="Modalitet"
              />
              <v-text-field
                v-if="isModalitetDrugo"
                ref="professionRef"
                v-model="fmodalitet2"
                label="Modalitet (uneti ručno)"
                :rules="rules.profession"
                required
              />

              <v-row
                justify="center"
                class="mt-6"
              >
                <v-btn
                  text
                  @click="step = 1"
                >
                  Nazad
                </v-btn>
                <v-btn
                  v-if="showContinue"
                  color="primary"
                  @click="step = 3"
                >
                  Dalje
                </v-btn>
              </v-row>
            </v-stepper-content>

            <!-- STEP 3 -->
            <v-stepper-content step="3">
              <v-text-field
                v-model="form.phone"
                label="Telefon"
                prepend-icon="mdi-phone"
                :rules="rules.phoneNumber"
                required
              />
              <v-text-field
                v-model="form.zoom"
                prepend-icon="mdi-webcam"
                label="ZOOM-ID"
                required
              />

              <v-row
                justify="center"
                class="mt-6"
              >
                <v-btn
                  text
                  @click="step = 2"
                >
                  Nazad
                </v-btn>
                <v-btn
                  color="primary"
                  type="submit"
                  :disabled="!valid"
                  @click.prevent="handleLogin"
                >
                  SUBMIT
                </v-btn>
              </v-row>
            </v-stepper-content>
          </v-form>
        </v-stepper-items>
      </v-stepper>
      <v-alert
        v-if="savedOk"
        type="info"
      >
        Uspešno ste registrovani. Sačekajte potvrdu.
      </v-alert>
    </v-col>
  </v-row>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { NewDoctor } from '@/model/NewDoctor';
import DoctorApi from '@/api/DoctorApi';
import { modalitets, occupations } from '@/utils/data';

@Component({
  name: 'Register',
})
export default class extends Vue {
  private step: number = 1;

  private valid = false;

  private showContinue = false;

  private form: NewDoctor = new NewDoctor();

  private fmodalitet2 : string = '';

  private savedOk = false;

  mounted() {
    this.showContinue = true;
  }

  get isOccupationDrugo() {
    return this.form.occupation === 999;
  }

  get isOccupationSpecial() {
    return this.form.occupation === 4 || this.form.occupation === 5;
  }

  get isModalitetDrugo() {
    return this.form.modalitet === 999;
  }

  get modalitetItems() {
    return modalitets;
  }

  get occupationItems() {
    return occupations;
  }

  private rules = {
    nameAndSurname: [
      (v: string) => !!v || 'Ime i prezime obavezni',
    ],
    email: [
      (v: string) => !!v || 'E-mail obavezan',
      (v: string) => /.+@.+\..+/.test(v) || 'E-mail mora biti validan',
      (v: string) => !!v && /.+@.+\..+/.test(v),
    ],
    year: [
      (v: number) => !!v || 'Godina rodjena obavezna',
      (v: number) => (v <= 2000 && v >= 1900) || 'Godina rodjena mora biti validna (1900 - 2000)',
      (v: number) => !!v && (v <= 2000 && v >= 1900),
    ],
    profession: [
      (v: number) => !!v || 'Zanimanje obavezno',
    ],
    eduYear: [
      (v: number) => !!v || 'Godine edukacije obavezne',
      (v: number) => (v <= 20 && v >= 0) || 'Godine edukacije moraju biti validne (0 - 20)',
      (v: number) => !!v && (v <= 20 && v >= 0),
    ],
    phoneNumber: [
      (v: number) => !!v || 'Broj telefona obavezan',
      (v: number) => v >= 0 || 'Broj telefona mora biti validan',
      (v: number) => !!v && v >= 0,
    ],

  };

  private async handleLogin() {
    await DoctorApi.postNewDoctor(this.form);
    this.savedOk = true;
  }
}
</script>
