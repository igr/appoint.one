<template>
  <main class="app-main">
    <transition
      name="fade-transform"
      mode="out-in"
    >
      <keep-alive>
        key: {{ key }}
        <router-view :key="key" />
      </keep-alive>
    </transition>
  </main>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component({
  name: 'AppMain',
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
