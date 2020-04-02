<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <h1 class="heading text-center">
        ZAKAZANI TERMIN #{{ id }}
      </h1>
      <div v-if="!isLoading">
        <day-big :datetime="datetime" />
        <p class="text-center">
          kod sledećeg psihotereapeuta:
        </p>
        <doctor-profile :doc="doctor" />
        <p style="text-align: center">
          Molimo zapamtite link do ove stranice i sve podatke.
        </p>
      </div>
      <div v-else>
        ELSE
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import TimeslotApi from '@/api/TimeslotApi';
// eslint-disable-next-line no-unused-vars
import { Timeslot } from '@/model/Timeslot';
// eslint-disable-next-line no-unused-vars
import { Doctor } from '@/model/Doctor';
// eslint-disable-next-line no-unused-vars
import { DateTime } from '@/model/DateTime';
import DoctorProfile from '@/components/DoctorProfile/index.vue';
import DayBig from '@/components/DayBig/index.vue';

@Component({
  name: 'Appointment',
  components: {
    DoctorProfile,
    DayBig,
  },
})
export default class extends Vue {
  @Prop({ default: -1 })
  readonly id!: number;

  private timeslot: Timeslot | undefined;

  private doctor: Doctor | undefined;

  private datetime: DateTime | undefined;

  private isLoading = true;

  async created() {
    this.timeslot = await this.fetchData();
  }

  private async fetchData() {
    const { data } = await TimeslotApi.get(this.id);
    this.timeslot = data;
    this.doctor = this.timeslot?.doctor;
    this.datetime = this.timeslot?.datetime;
    console.log(this.doctor);
    this.isLoading = false;
    return data;
  }
}
</script>

<style scoped>
</style>
