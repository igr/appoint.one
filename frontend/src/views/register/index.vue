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
                ref="securityCodeRef"
                v-model="form.regCode"
                prepend-icon="mdi-key"
                label="Sigurnosni kod (dobijen iz udruženja)"
                :rules="rules.required"
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
                  :disabled="!($refs.securityCodeRef.valid)"
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
                :rules="rules.required"
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
                ref="passwordRef"
                v-model="form.password"
                label="Lozinka"
                type="password"
                prepend-icon="mdi-textbox-password"
                :rules="rules.password"
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
                    $refs.yearRef.valid &&
                    $refs.passwordRef.valid)"
                  @click="step = 3"
                >
                  Dalje
                </v-btn>
              </v-row>
            </v-stepper-content>

            <!-- STEP 3 -->
            <v-stepper-content step="3">
              <v-autocomplete
                ref="professionRef"
                v-model="form.doctor.occupation"
                :items="occupationItems"
                hide-no-data
                hide-selected
                :rules="rules.required"
                label="Zanimanje"
              />
              <v-text-field
                v-show="isOccupationDrugo"
                ref="professionDrugoRef"
                v-model="form.doctor.occupation2"
                label="Zanimanje (uneti ručno)"
                :rules="rules.required"
                required
              />
              <v-text-field
                v-show="isOccupationSpecial"
                ref="professionSpecialRef"
                v-model="form.doctor.occupationSpec"
                label="Vrsta specializacije"
                :rules="rules.required"
                required
              />

              <v-radio-group
                ref="certificateRef"
                v-model="form.doctor.certificate"
                label="Sertifikat"
                :rules="rules.certificate"
                row
              >
                <v-radio
                  label="Ne"
                  :value="0"
                />
                <v-radio
                  label="Da - Domaći"
                  :value="1"
                />
                <v-radio
                  label="Da - Inostrani"
                  :value="2"
                />
              </v-radio-group>
              <v-text-field
                v-show="!hasCertificate"
                ref="educationYearRef"
                v-model="form.doctor.education"
                label="Godina edukacije"
                type="number"
                :rules="rules.educationYear"
                required
              />
              <v-autocomplete
                ref="modalitetRef"
                v-model="form.doctor.modalitet"
                :items="modalitetItems"
                hide-no-data
                hide-selected
                :rules="rules.required"
                label="Modalitet"
              />
              <v-text-field
                v-show="isModalitetDrugo"
                ref="modalitetDrugoRef"
                v-model="form.doctor.modalitet2"
                label="Modalitet (uneti ručno)"
                :rules="rules.required"
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
                    $refs.professionRef.valid &&
                    $refs.certificateRef.valid &&
                    $refs.modalitetRef.valid &&
                    (isOccupationDrugo ? $refs.professionDrugoRef.valid : true) &&
                    (isOccupationSpecial ? $refs.professionSpecialRef.valid : true) &&
                    (isModalitetDrugo ? $refs.modalitetDrugoRef.valid : true) &&
                    (!hasCertificate ? $refs.educationYearRef.valid : true))"
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
                label="Telefon (u međunarodnom formatu, na pr.: +3816411111)"
                prepend-icon="mdi-phone"
                :rules="rules.phoneNumber"
                required
              />
              <v-text-field
                ref="zoomRef"
                v-model="form.doctor.zoom"
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
                  @click="step = 3"
                >
                  Nazad
                </v-btn>
                <v-btn
                  v-if="showContinue"
                  color="primary"
                  type="submit"
                  :disabled="!($refs.phoneRef.valid)"
                  @click.prevent="handleLogin"
                >
                  POŠALJI
                </v-btn>
              </v-row>
            </v-stepper-content>
          </v-form>
        </v-stepper-items>
      </v-stepper>
    </v-col>
  </v-row>
</template>


<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { NewDoctor } from '@/model/NewDoctor';
import DoctorApi from '@/api/DoctorApi';
import { modalitets, occupations } from '@/utils/data';
import { validationRules } from '@/utils/validate';
import { DoctorData } from '@/model/DoctorData';

@Component({
  name: 'Register',
})
export default class extends Vue {
  private step = 1;

  private valid = false;

  private showContinue = false;

  private form: NewDoctor = {
    doctor: {} as DoctorData,
  } as NewDoctor;

  mounted() {
    this.showContinue = true;
  }

  get hasCertificate() {
    return this.form.doctor.certificate !== 0;
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
    ...validationRules,
    year: [
      (v: number) => !!v || 'Godina rođena je obavezna',
      (v: number) => (v <= 2002 && v >= 1900) || 'Godina rođena mora biti validna (1900 - 2002)',
    ],
    certificate: [
      (v: number) => (v >= 0 && v <= 3) || 'Polje je obavezno',
    ],
    educationYear: [
      (v: number) => !!v || 'Godine edukacije su obavezne',
      (v: number) => (v <= 20 && v >= 0) || 'Godine edukacije moraju biti validne (0 - 20)',
    ],
  };

  private async handleLogin() {
    if (!this.form.doctor.zoom) {
      this.form.doctor.zoom = '';
    }
    await DoctorApi.postNewDoctor(this.form);
    await this.$router.push('/register-ok');
  }
}
</script>
