<template>
  <div>
    <HelloWorld msg="Welcome to App" />
    <NumberOfTimeslots />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import HelloWorld from '@/components/HelloWorld.vue';
import NumberOfTimeslots from '@/components/NumberOfTimeslots.vue';

@Component({
  name: 'Home',
  components: {
    HelloWorld,
    NumberOfTimeslots,
  },
})
export default class extends Vue {
}
</script>

<style lang="scss" scoped></style>
