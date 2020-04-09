<template>
  <v-row
    justify="center"
    align="center"
  >
    <v-dialog
      v-model="dialog2.show"
      max-width="290"
    >
      <v-card>
        <v-card-title class="headline">
          Potvrdite
        </v-card-title>

        <v-card-text>
          {{ dialog2.text }}
        </v-card-text>

        <v-card-actions>
          <v-spacer />

          <v-btn
            color="green darken-1"
            text
            @click="dialog2.show = false"
          >
            NE
          </v-btn>

          <v-btn
            color="green darken-1"
            text
            @click="dialogOk()"
          >
            DA
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-col
      cols="12"
      md="8"
    >
      <h1 class="heading text-center">
        TERMIN #{{ id }}
      </h1>
      <div v-if="!isLoading">
        <day-big :datetime="timeslot && timeslot.datetime" />
        <h2 style="text-align: center">
          status: <span class="yellow">{{ status }}</span>
        </h2>
        <v-btn
          x-large
          class="ma-4 col-12"
          color="primary"
          @click.stop="removeTimeslot0()"
        >
          <v-icon class="mr-4">
            mdi-minus
          </v-icon>
          Obriši termin
        </v-btn>
        <v-btn
          x-large
          class="ma-4 col-12"
          color="blue"
          @click.stop="cancelTimeslot0()"
        >
          <v-icon class="mr-4">
            mdi-cancel
          </v-icon>
          Otkaži termin
        </v-btn>
        <v-btn
          x-large
          class="ma-4 col-12"
          color="green"
          @click.stop="doneTimeslot()"
        >
          <v-icon class="mr-4">
            mdi-check
          </v-icon>
          Završeno
        </v-btn>
        <v-btn
          x-large
          class="ma-4 col-12"
          to="/my"
        >
          NAZAD
        </v-btn>
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
import DoctorProfile from '@/components/DoctorProfile/index.vue';
import DayBig from '@/components/DayBig/index.vue';
import AppoitmentApi from '@/api/AppoitmentApi';
import TimeslotApi from '@/api/TimeslotApi';

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

  private dialog2 = {
    show: false,
    text: '',
    action: -1,
  };

  private timeslot: Timeslot | undefined;

  private isLoading = true;

  async created() {
    this.isLoading = true;
    await this.fetchData();
  }

  private async fetchData() {
    const { data } = await AppoitmentApi.get(this.id);
    this.timeslot = data.timeslot;
    this.isLoading = false;
  }

  async removeTimeslot0() {
    this.dialog2.text = 'Da li želite da obrište termin?';
    this.dialog2.action = 1;
    this.dialog2.show = true;
  }

  async removeTimeslot() {
    await TimeslotApi.delete(this.id);
    await this.$router.push('/my');
  }

  async cancelTimeslot0() {
    this.dialog2.text = 'Da li želite da označite termin kao OTKAZAN?';
    this.dialog2.action = 2;
    this.dialog2.show = true;
  }

  async cancelTimeslot() {
    await TimeslotApi.cancelTimeslot(this.id);
    await this.$router.push('/my');
  }

  dialogOk() {
    const { action } = this.dialog2;
    this.dialog2.show = false;
    switch (action) {
      case 1: this.removeTimeslot(); break;
      case 2: this.cancelTimeslot(); break;
      default: throw Error('BAD action');
    }
  }

  doneTimeslot() {
    this.$router.push(`/evaluation/${this.id}`);
  }

  get status() {
    switch (this.timeslot?.status) {
      case 0: return 'AKTIVAN';
      case 10: return 'REZERVISAN';
      case 20: return 'OTKAZAN';
      case 30: return 'ZAVRŠEN';
      default: return 'NEPOZNAT';
    }
  }
}
</script>

<style scoped>
</style>
