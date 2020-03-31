<template>
  <div>
    <el-steps :active="active" align-center finish-status="success">
      <el-step title="Step 1" />
      <el-step title="Step 2" />
      <el-step title="Step 3" />
    </el-steps>

    <el-form>
      <div v-show="active === 0">
        <el-form-item>
          <el-input v-model="form.name" placeholder="Ime i prezime" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="form.sex" placeholder="Pol" style="width: 100%">
            <el-option label="Muški" value="M" />
            <el-option label="ženski" value="F" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.year" placeholder="Godište" type="number" />
        </el-form-item>
      </div>
      <div v-show="active === 1">
        <el-form-item>
          <el-input v-model="form.mesto" placeholder="Mesto" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.year" placeholder="Osnovno zanimanje" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.year" placeholder="Godine edukacije" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.year" placeholder="Psihoterapisj" />
        </el-form-item>
      </div>
      <div v-show="active === 2">
        <el-form-item>
          <el-input v-model="form.mesto" placeholder="Mesto" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.year" placeholder="Osnovno zanimanje" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.year" placeholder="Godine edukacije" />
        </el-form-item>
      </div>
    </el-form>

    <el-button round type="primary" @click="next">
      Next step
    </el-button>
  </div>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component({
  name: 'Register',
})
export default class extends Vue {
  private active: number = 0;

  private form = {
    name: '',
    sex: '',
    year: '',
  };

  next() {
    this.active += 1;
    if (this.active > 2) {
      this.active = 0;
    }
  }
}
</script>
