<template>
  <div>
    <HelloWorld msg="Welcome to App" />
    <div>
      <router-link to="/doctor">
        Doktori
      </router-link>
    </div>
    <div>
      <router-link to="/login">
        Login
      </router-link>
    </div>
    <div><a href="/user">Korisnici</a></div>
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
