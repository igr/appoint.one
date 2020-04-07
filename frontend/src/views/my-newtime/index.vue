<template>
  <v-row
    justify="center"
    align="center"
  >
    <v-col
      cols="12"
      md="6"
    >
      <h1>Novi termin</h1>
      <h2>Datum</h2>
      <v-date-picker
        v-model="date"
        full-width
        :first-day-of-week="0"
        locale="sr-Latn"
        :landscape="$vuetify.breakpoint.smAndUp"
        class="mt-4"
      />
      <h2>Vreme</h2>
      <v-time-picker
        v-model="picker"
        full-width
        format="24hr"
        :landscape="$vuetify.breakpoint.smAndUp"
      />
      <div class="mt-6">
        <v-btn
          x-large
          to="/my"
          class="mr-6"
        >
          NAZAD
        </v-btn>
        <v-btn
          type="submit"
          color="primary"
          x-large
          @click="save()"
        >
          SAČUVAJ
        </v-btn>
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { Doctor } from '@/model/Doctor';
import { UserModule } from '@/store/modules/user';
import { toDateTime } from '@/utils/time';
import TimeslotApi from '@/api/TimeslotApi';
import { AppModule } from '@/store/modules/app';

@Component({
  name: 'Appointment',
})
export default class extends Vue {
  get doctor(): Doctor {
    return UserModule.doctor;
  }

  private date: string = new Date().toISOString().substr(0, 10);

  private picker = '00:00';

  async save() {
    const dt = toDateTime(this.date, this.picker);
    try {
      await TimeslotApi.post(this.doctor.id, dt);
      await this.$router.push('/my');
      AppModule.setInfo({ message: 'Termin je sačuvan', type: 'info' });
    } catch (err) {
      AppModule.setInfo({ message: 'Greška', type: 'error' });
    }
  }
}
</script>

<style scoped>
</style>
