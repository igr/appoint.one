<template>
  <v-row
    justify="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <h1 class="heading mb-6">
        Izmena profila
      </h1>
      <v-form
        ref="form"
      >
        <v-text-field
          ref="nameRef"
          v-model="doctor.data.name"
          label="Ime i prezime"
          :rules="rules.required"
          prepend-icon="mdi-human"
          required
        />
        <v-text-field
          ref="emailRef"
          v-model="doctor.data.email"
          label="Email"
          type="email"
          prepend-icon="mdi-email"
          :rules="rules.email"
          required
        />
        <v-text-field
          ref="phoneRef"
          v-model="doctor.data.phone"
          label="Telefon"
          prepend-icon="mdi-phone"
          :rules="rules.phoneNumber"
          required
        />
        <v-text-field
          ref="zoomRef"
          v-model="doctor.data.zoom"
          prepend-icon="mdi-webcam"
          label="ZOOM-ID"
          required
        />
        <v-btn
          color="primary"
          type="submit"
          @click.prevent="saveDoctor"
        >
          IZMENI
        </v-btn>
      </v-form>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { UserModule } from '@/store';
import { Doctor } from '@/model/Doctor';
import { validationRules } from '@/utils/validate';
import DoctorApi from '@/api/DoctorApi';

@Component({
  name: 'Doctor',
})
export default class extends Vue {
  private doctor = {} as Doctor;

  created() {
    this.doctor = UserModule.doctor;
  }

  private rules = {
    ...validationRules,
  }

  private async saveDoctor() {
    await DoctorApi.updateDoctorData(this.doctor.id, this.doctor.data);
    await this.$router.push('/my');
  }
}
</script>

<style scoped>
</style>
