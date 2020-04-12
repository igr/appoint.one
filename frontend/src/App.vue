<template>
  <v-app>
    <Alert />
    <Confirm ref="confirm" />
    <router-view />
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Alert from '@/components/Alert/index.vue';
import Confirm from '@/components/Confirm.vue';

@Component({
  components: {
    Alert,
    Confirm,
  },
})
export default class App extends Vue {
  $root!: Vue & {
    $confirm: (title: string, message: string, options?: any) => Promise<any>;
  }

  $refs!: {
    confirm: Vue & {
      open: (title: string, message: string, options?: any) => Promise<any>;
    };
  }

  mounted() {
    if (this.$refs.confirm) this.$root.$confirm = this.$refs.confirm.open;
  }
}
</script>
