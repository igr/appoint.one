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
    <div>
      <a
        href="#"
        @click="logout"
      >
        Logout
      </a>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import HelloWorld from '@/components/HelloWorld.vue';
import { UserModule } from '@/store/modules/user';

@Component({
  name: 'Home',
  components: {
    HelloWorld,
  },
})
export default class extends Vue {
  logout() {
    UserModule.LogOut();
  }
}
</script>

<style lang="scss" scoped></style>
