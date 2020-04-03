<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <h1 class="heading">
        {{ doctor.data.name }}
      </h1>
      <div class="subtitle-1">
        {{ doctor.data.occupation }}
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
// import TimeslotApi from '@/api/TimeslotApi';
import { UserModule } from '@/store/modules/user';
// eslint-disable-next-line no-unused-vars
import { Doctor } from '@/model/Doctor';

@Component({
  name: 'My',
})
export default class extends Vue {
  get doctor(): Doctor {
    return UserModule.doctor;
  }

  async created() {
    console.log(this.doctor.id);
    // this.timeslot = await this.fetchData();
  }

  private async fetchData() {
    // const userId = UserModule.id;
    // TimeslotApi.listMyTimeslots()
  }
}
</script>

<style lang="scss" scoped></style>
