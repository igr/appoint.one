<template>
  <div
    :class="classObj"
    class="app-wrapper"
  >
    !Layout
    <app-main />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { DeviceType } from '@/store/modules/app';
import { AppMain } from './components';

@Component({
  name: 'Layout',
  components: {
    AppMain,
  },
})
export default class extends Vue {
  private device: DeviceType = DeviceType.Desktop;

  get classObj() {
    return {
      mobile: this.device === DeviceType.Mobile,
    };
  }
}
</script>

<style lang="scss" scoped>
.app-wrapper {
  position: relative;
  height: 100%;
  width: 100%;
}

.main-container {
  min-height: 100%;
  transition: margin-left .28s;
  position: relative;
}
</style>
