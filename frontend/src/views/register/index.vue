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
                required
              />
              <v-text-field
                ref="emailRef"
                v-model="form.email"
                label="Email"
                type="email"
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
                  v-if="showContinue1"
                  color="primary"
                  @click="step = 2"
                  :disabled="!($refs.nameRef.valid && $refs.emailRef.valid && $refs.yearRef.valid)"
                >
                  Continue
                </v-btn>
              </v-row>
            </v-stepper-content>

            <!-- STEP 2 -->
            <v-stepper-content step="2">
              <v-text-field
                ref="professionRef"
                v-model="form.occupation"
                label="Zanimanje"
                :rules="rules.profession"
                required
              />
              <v-text-field
                ref="eduYearRef"
                v-model="form.education"
                label="Godine edukacije"
                type="number"
                :rules="rules.eduYear"
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
                  v-if="showContinue2"
                  color="primary"
                  @click="step = 3"
                  :disabled="!($refs.professionRef.valid && $refs.eduYearRef.valid)"
                >
                  Continue
                </v-btn>
              </v-row>
            </v-stepper-content>

            <!-- STEP 3 -->
            <v-stepper-content step="3">
              <v-text-field
                v-model="form.phone"
                label="Telefon"
                :rules="rules.phoneNumber"
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

@Component({
  name: 'Register',
})
export default class extends Vue {
  private step: number = 1;

  private valid = false;

  private showContinue1 = false;

  private showContinue2 = false;

  private form: NewDoctor = new NewDoctor();

  private savedOk = false;

  mounted() {
    this.showContinue1 = true;
    this.showContinue2 = true;
    console.log(this.$refs.id1);
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
