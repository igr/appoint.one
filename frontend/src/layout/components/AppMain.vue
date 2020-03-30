<template>
  <div>
    <transition
      name="fade-transform"
      mode="out-in"
    >
      <keep-alive>
        key: {{ key }}
        <router-view :key="key" />
      </keep-alive>
    </transition>
  </div>
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

<style>
</style>
