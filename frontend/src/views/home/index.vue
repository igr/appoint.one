<template>
  <div>
    <HelloWorld msg="Welcome to App" />
    <div>
      <router-link to="/doctor">
        Doktori
      </router-link>
    </div>
    <div>
      <router-link to="/inquire">
        Korisnici
      </router-link>
    </div>
    <div>
      <router-link to="/login">
        Login
      </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import HelloWorld from '@/components/HelloWorld.vue';

@Component({
  name: 'Home',
  components: {
    HelloWorld,
  },
})
export default class extends Vue {
}
</script>

<style lang="scss" scoped></style>
