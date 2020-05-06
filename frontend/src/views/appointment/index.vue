<template>
  <v-row
    justify="center"
    align="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <h1 class="heading text-center">
        ZAKAZANI TERMIN #{{ id }}
      </h1>
      <div v-if="!isLoading">
        <day-big :datetime="timeslot && timeslot.datetime" />

        <v-row justify="center">
          <CalendarIcsButton
            :timeslot="timeslot"
          />
        </v-row>
        <v-row justify="center">
          <CalendarGoogleButton
            :timeslot="timeslot"
            :doctor="doctor"
          />
        </v-row>
        <!--v-row justify="center">
          <v-btn
            dark
            x-large
            color="primary"
            :to="{ path: `/room/${id}` }"
            class="ma-2 col-10 col-md-4"
          >
            <v-icon
              dark
              class="mr-2"
            >
              mdi-headset
            </v-icon>
            Uđi u sobu za razgovor
          </v-btn>
        </v-row-->

        <doctor-profile :doc="doctor" />
        <p class="text-center text-uppercase mt-6">
          Sačuvajte link do ove stranice i podatke.<br>
          U naznačeno vreme pozovite psihoterapeuta.<br>
        </p>
      </div>
      <div v-else>
        {{ $t('msg.pleaseWait') }}
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import { Timeslot } from '@/model/Timeslot';
import { Doctor } from '@/model/Doctor';
import DoctorProfile from '@/components/DoctorProfile/index.vue';
import DayBig from '@/components/DayBig/index.vue';
import CalendarIcsButton from '@/components/CalendarIcsButton/index.vue';
import CalendarGoogleButton from '@/components/CalendarGoogleButton/index.vue';
import AppoitmentApi from '@/api/AppoitmentApi';

@Component({
  name: 'Appointment',
  components: {
    DoctorProfile,
    DayBig,
    CalendarIcsButton,
    CalendarGoogleButton,
  },
})
export default class extends Vue {
  @Prop({ default: -1 })
  readonly id!: number;

  private doctor: Doctor | undefined;

  private timeslot: Timeslot | undefined;

  private isLoading = true;

  async created() {
    this.isLoading = true;
    await this.fetchData();
  }

  private async fetchData() {
    const { data } = await AppoitmentApi.get(this.id);
    this.doctor = data.doctor;
    this.timeslot = data.timeslot;
    this.isLoading = false;
  }
}
</script>

<style scoped>
</style>
