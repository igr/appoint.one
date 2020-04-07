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
            Kod
          </v-stepper-step>
          <v-stepper-step
            :complete="step > 2"
            step="2"
          >
            Lično
          </v-stepper-step>
          <v-divider />
          <v-stepper-step
            :complete="step > 3"
            step="3"
          >
            Obrazovanje
          </v-stepper-step>
          <v-divider />
          <v-stepper-step step="4">
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
                prepend-icon="mdi-key"
                label="Sigurnosni kod (dobijen iz udruženja)"
                v-model="form.regCode"
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
                  Odustani
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
                ref="nameRef"
                v-model="form.doctor.name"
                label="Ime i prezime"
                :rules="rules.nameAndSurname"
                prepend-icon="mdi-human"
                required
              />
              <v-text-field
                ref="emailRef"
                v-model="form.doctor.email"
                label="Email"
                type="email"
                prepend-icon="mdi-email"
                :rules="rules.email"
                required
              />
              <v-text-field
                ref="passRef"
                v-model="form.password"
                label="Lozinka"
                type="password"
                prepend-icon="mdi-textbox-password"
                required
              />
              <v-radio-group
                v-model="form.doctor.sex"
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
                v-model="form.doctor.year"
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
                  @click="step = 1"
                >
                  Nazad
                </v-btn>
                <v-btn
                  v-if="showContinue"
                  color="primary"
                  :disabled="!(
                    $refs.nameRef.valid &&
                    $refs.emailRef.valid &&
                    $refs.yearRef.valid)"
                  @click="step = 3"
                >
                  Dalje
                </v-btn>
              </v-row>
            </v-stepper-content>

            <!-- STEP 3 -->
            <v-stepper-content step="3">
              <v-text-field
                ref="educationRef"
                v-model="form.doctor.education"
                label="Godina edukacije"
                type="number"
                :rules="rules.educationYear"
                required
              />
              <v-autocomplete
                ref="professionRef"
                v-model="form.doctor.occupation"
                :items="occupationItems"
                hide-no-data
                hide-selected
                :rules="rules.profession"
                label="Zanimanje"
              />
              <v-text-field
                v-show="isOccupationDrugo"
                ref="professionDrugoRef"
                v-model="form.doctor.occupation2"
                label="Zanimanje (uneti ručno)"
                :rules="rules.professionDrugo"
                required
              />
              <v-text-field
                v-show="isOccupationSpecial"
                ref="professionSpecialRef"
                v-model="form.doctor.occupationSpec"
                label="Vrsta specializacije"
                :rules="rules.professionSpecial"
                required
              />

              <v-radio-group
                v-model="form.doctor.certificate"
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
                ref="modalitetRef"
                v-model="form.doctor.modalitet"
                :items="modalitetItems"
                hide-no-data
                hide-selected
                :rules="rules.modalitet"
                label="Modalitet"
              />
              <v-text-field
                v-show="isModalitetDrugo"
                ref="modalitetDrugoRef"
                v-model="fmodalitet2"
                label="Modalitet (uneti ručno)"
                :rules="rules.modalitetDrugo"
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
                  :disabled="!(
                    $refs.educationRef.valid &&
                    $refs.professionRef.valid &&
                    $refs.modalitetRef.valid &&
                    (isOccupationDrugo ? $refs.professionDrugoRef.valid : true) &&
                    (isOccupationSpecial ? $refs.professionSpecialRef.valid : true) &&
                    (isModalitetDrugo ? $refs.modalitetDrugoRef.valid : true))"
                  @click="step = step + 1"
                >
                  Dalje
                </v-btn>
              </v-row>
            </v-stepper-content>

            <!-- STEP 4 -->
            <v-stepper-content step="4">
              <v-text-field
                ref="phoneRef"
                v-model="form.doctor.phone"
                label="Telefon"
                prepend-icon="mdi-phone"
                :rules="rules.phoneNumber"
                required
              />
              <v-text-field
                ref="zoomRef"
                v-model="form.doctor.zoom"
                prepend-icon="mdi-webcam"
                label="ZOOM-ID"
                :rules="rules.zoomID"
                required
              />

              <v-row
                justify="center"
                class="mt-6"
              >
                <v-btn
                  text
                  @click="step = 3"
                >
                  Nazad
                </v-btn>
                <v-btn
                  v-if="showContinue"
                  color="primary"
                  type="submit"
                  :disabled="!(
                    $refs.phoneRef.valid &&
                    $refs.zoomRef.valid)"
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
// eslint-disable-next-line max-classes-per-file
import { Component, Vue } from 'vue-property-decorator';
import { NewDoctor } from '@/model/NewDoctor';
import DoctorApi from '@/api/DoctorApi';
import { modalitets, occupations } from '@/utils/data';
import { isValidEmail, isValidPhoneNumber, isValidZoomID } from '@/utils/validate';
import { DoctorData } from '@/model/DoctorData';

@Component({
  name: 'Register',
})
export default class extends Vue {
  private step = 1;

  private valid = false;

  private showContinue = false;

  private form: NewDoctor = new class implements NewDoctor {
    doctor: DoctorData = new class implements DoctorData {
      certificate = -1;

      education = 0;

      email = '';

      modalitet = -1;

      modalitet2 = '';

      name = '';

      occupation = -1;

      occupation2 = '';

      occupationSpec = '';

      phone = '';

      sex = false;

      year = 0;

      zoom = '';
    }();

    password = '';

    regCode = '';
  }();

  private fmodalitet2 = '';

  private savedOk = false;

  mounted() {
    this.showContinue = true;
  }

  get isOccupationDrugo() {
    return this.form.doctor.occupation === 999;
  }

  get isOccupationSpecial() {
    return this.form.doctor.occupation === 4 || this.form.doctor.occupation === 5;
  }

  get isModalitetDrugo() {
    return this.form.doctor.modalitet === 999;
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
      (v: string) => isValidEmail(v) || 'E-mail mora biti validan',
      (v: string) => !!v && isValidEmail(v),
    ],
    year: [
      (v: number) => !!v || 'Godina rodjena obavezna',
      (v: number) => (v <= 2000 && v >= 1900) || 'Godina rodjena mora biti validna (1900 - 2000)',
      (v: number) => !!v && (v <= 2000 && v >= 1900),
    ],
    educationYear: [
      (v: number) => !!v || 'Godine edukacije obavezne',
      (v: number) => (v <= 20 && v >= 0) || 'Godine edukacije moraju biti validne (0 - 20)',
      (v: number) => !!v && (v <= 20 && v >= 0),
    ],
    profession: [
      (v: number) => !!v || 'Zanimanje obavezno',
    ],
    professionDrugo: [
      (v: number) => !!v || 'Drugo zanimanje obavezno',
    ],
    professionSpecial: [
      (v: number) => !!v || 'Vrsta specijalizacije obavezna',
    ],
    modalitet: [
      (v: number) => !!v || 'Modalitet obavezan',
    ],
    modalitetDrugo: [
      (v: number) => !!v || 'Modalitet obavezan',
    ],
    phoneNumber: [
      (v: string) => !!v || 'Broj telefona obavezan',
      (v: string) => isValidPhoneNumber(v) || 'Broj telefona mora biti validan: sadrži cifre i znakove: /, ,-',
      (v: string) => !!v && isValidPhoneNumber(v),
    ],
    zoomID: [
      (v: number) => !!v || 'Zoom ID obavezan',
      (v: number) => isValidZoomID(v) || 'Zoom ID mora biti validan',
      (v: number) => !!v && isValidZoomID(v),
    ],

  };

  private async handleLogin() {
    await DoctorApi.postNewDoctor(this.form);
    this.savedOk = true;
  }
}
</script>
