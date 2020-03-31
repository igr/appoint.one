<template>
  <v-row>
    <v-col cols="12">
      <h1>Registracija</h1>

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
              <v-radio-group
                v-model="form.sex"
                :row="true"
                mandatory
                required
              >
                <v-radio
                  label="Man"
                  value="true"
                />
                <v-radio
                  label="Woman"
                  value="false"
                />
              </v-radio-group>
              <v-text-field
                v-model="form.year"
                label="Godina rođenja"
                type="number"
                required
              />

              <v-btn
                color="primary"
                @click="step = 2"
              >
                Continue
              </v-btn>
              <v-btn text>
                Cancel
              </v-btn>
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

              <v-btn
                color="primary"
                @click="step = 3"
              >
                Continue
              </v-btn>

              <v-btn text>
                Cancel
              </v-btn>
            </v-stepper-content>

            <!-- STEP 3 -->
            <v-stepper-content step="3">
              <v-text-field
                v-model="form.phone"
                label="Telefon"
                required
              />

              <v-btn
                color="primary"
                type="submit"
                :disabled="!valid"
                @click="handleLogin"
              >
                SUBMIT
              </v-btn>

              <v-btn text>
                Cancel
              </v-btn>
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

@Component({
  name: 'Register',
})
export default class extends Vue {
  private step: number = 1;

  private valid = false;

  private form: NewDoctor = new NewDoctor();

  private async handleLogin() {
    await DoctorApi.postNewDoctor(this.form);
    return false;
    // this.$router.push({
    //   path: this.redirect || '/',
    // });
  }
}
</script>
