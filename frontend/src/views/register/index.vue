<template>
  <v-row><v-col cols="12">
    <h1>Registracija</h1>
    <v-stepper v-model="step" :vertical="false">
      <v-stepper-header>
        <v-stepper-step :complete="step > 1" step="1">
          Podaci
        </v-stepper-step>
        <v-divider/>

        <v-stepper-step :complete="step > 2" step="2">
          Name of step 2
        </v-stepper-step>

        <v-divider/>

        <v-stepper-step step="3">
          Name of step 3
        </v-stepper-step>
      </v-stepper-header>


      <v-stepper-items>
        <v-stepper-content step="1">
          <v-text-field v-model="form.name" label="Ime i prezime" />

          <v-btn color="primary" @click="step = 2">
            Continue
          </v-btn>

          <v-btn text>
            Cancel
          </v-btn>
        </v-stepper-content>

        <v-stepper-content step="2">
          <v-card
            class="mb-12"
            color="grey lighten-1"
            height="200px"
          />

          <v-btn
            color="primary"
            @click="step = 3"
          >
            Continue
          </v-btn>

          <v-btn text>
            Cancel
          </v-btn>
        </v-stepper-content>

        <v-stepper-content step="3">
          <v-card
            class="mb-12"
            color="grey lighten-1"
            height="200px"
          />

          <v-btn
            color="primary"
            @click="step = 1"
          >
            Continue
          </v-btn>

          <v-btn text>
            Cancel
          </v-btn>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>

    <!--    <el-steps :active="active" align-center finish-status="success">-->
    <!--      <el-step title="Step 1" />-->
    <!--      <el-step title="Step 2" />-->
    <!--      <el-step title="Step 3" />-->
    <!--    </el-steps>-->

    <!--    <el-form>-->
    <!--      <div v-show="active === 0">-->
    <!--        <el-form-item>-->
    <!--          <el-input v-model="form.name" placeholder="Ime i prezime" />-->
    <!--        </el-form-item>-->
    <!--        <el-form-item>-->
    <!--          <el-select v-model="form.sex" placeholder="Pol" style="width: 100%">-->
    <!--            <el-option label="Muški" value="M" />-->
    <!--            <el-option label="ženski" value="F" />-->
    <!--          </el-select>-->
    <!--        </el-form-item>-->
    <!--        <el-form-item>-->
    <!--          <el-input v-model="form.year" placeholder="Godište" type="number" />-->
    <!--        </el-form-item>-->
    <!--      </div>-->
    <!--      <div v-show="active === 1">-->
    <!--        <el-form-item>-->
    <!--          <el-input v-model="form.mesto" placeholder="Mesto" />-->
    <!--        </el-form-item>-->
    <!--        <el-form-item>-->
    <!--          <el-input v-model="form.year" placeholder="Osnovno zanimanje" />-->
    <!--        </el-form-item>-->
    <!--        <el-form-item>-->
    <!--          <el-input v-model="form.year" placeholder="Godine edukacije" />-->
    <!--        </el-form-item>-->
    <!--        <el-form-item>-->
    <!--          <el-input v-model="form.year" placeholder="Psihoterapisj" />-->
    <!--        </el-form-item>-->
    <!--      </div>-->
    <!--      <div v-show="active === 2">-->
    <!--        <el-form-item>-->
    <!--          <el-input v-model="form.mesto" placeholder="Mesto" />-->
    <!--        </el-form-item>-->
    <!--        <el-form-item>-->
    <!--          <el-input v-model="form.year" placeholder="Osnovno zanimanje" />-->
    <!--        </el-form-item>-->
    <!--        <el-form-item>-->
    <!--          <el-input v-model="form.year" placeholder="Godine edukacije" />-->
    <!--        </el-form-item>-->
    <!--      </div>-->
    <!--    </el-form>-->

    <!--    <el-button round type="primary" @click="next">-->
    <!--      Next step-->
    <!--    </el-button>-->
  </v-col></v-row>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component({
  name: 'Register',
})
export default class extends Vue {
  private step: number = 1;

  private form = {
    name: '',
    sex: '',
    year: '',
  };
}
</script>
