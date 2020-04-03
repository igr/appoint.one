<template>
  <v-row justify="center">
    <v-col
      cols="6"
    >
      <v-card
        color="red"
        outlined
      >
        <v-card-text class="text-center font-weight-bold pa-8" style="font-size: 2em;">
          {{ dateStr(datetime) }}
        </v-card-text>
      </v-card>
      <v-card
        color="gray"
        outlined
      >
        <v-card-text class="text-center font-weight-bold pa-6" style="font-size: 2em;">
          {{ timeStr(datetime) }}
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
// eslint-disable-next-line no-unused-vars
import { DateTime } from '@/model/DateTime';
import { toDateString, toTimeString } from '@/utils/time';

@Component
export default class DayBig extends Vue {
  @Prop() private datetime!: DateTime;

  dateStr(datetime: DateTime) {
    return toDateString(datetime);
  }

  timeStr(datetime: DateTime) {
    return toTimeString(datetime);
  }
}
</script>
