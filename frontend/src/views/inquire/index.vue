<template>
  <div>
    <h1>IZBOR TERMINA</h1>

    <NumberOfTimeslots />

    <pre>
      Ulaz: Država i Grad (ako nisu u Cookie)
    </pre>
    <AvailableTimeslots />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import AvailableTimeslots from '@/components/AvailableTimeslots/index.vue';
import NumberOfTimeslots from '@/components/NumberOfTimeslots/index.vue';

@Component({
  name: 'Inquiry',
  components: {
    AvailableTimeslots,
    NumberOfTimeslots,
  },
})
export default class extends Vue {
}
</script>

<style scoped>
</style>
