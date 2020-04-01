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
                v-model="form.name"
                label="Ime i prezime"
                required
              />
              <v-text-field
                v-model="form.email"
                label="Email"
                required
                type="email"
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
                v-model="form.year"
                label="Godina rođenja"
                type="number"
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
                  color="primary"
                  @click="step = 2"
                >
                  Continue
                </v-btn>
              </v-row>
            </v-stepper-content>

            <!-- STEP 2 -->
            <v-stepper-content step="2">
              <v-text-field
                v-model="form.occupation"
                label="Zanimanje"
                required
              />
              <v-text-field
                v-model="form.education"
                label="Godine edukacije"
                type="number"
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
                  color="primary"
                  @click="step = 3"
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

  private form: NewDoctor = new NewDoctor();

  private savedOk = false;

  private async handleLogin() {
    await DoctorApi.postNewDoctor(this.form);
    this.savedOk = true;
  }
}
</script>
