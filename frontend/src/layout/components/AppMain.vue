<template>
  <section class="app-main">
    key: {{ key }}
    <home />
    <router-view :key="key" />
  </section>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Home from '@/views/home/index.vue';

@Component({
  name: 'AppMain',
  components: {
    Home,
  },
})
export default class extends Vue {
  get key() {
    return this.$route.path;
  }
}
</script>

<style lang="scss" scoped>
.app-main {
  min-height: calc(100vh);
  width: 100%;
  position: relative;
  overflow: hidden;
}
</style>
