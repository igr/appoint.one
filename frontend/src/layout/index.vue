<template>
  <div>
    <el-container>
      <el-header>Header</el-header>
      <el-main><app-main /></el-main>
    </el-container>
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

<style>
</style>
