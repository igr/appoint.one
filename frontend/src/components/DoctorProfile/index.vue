<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <v-card>
        <v-card-text
          align="center"
        >
          <p class="display-2 text--primary">
            {{ doc.data.name }}
          </p>
          <p>{{ occupationText(doc) }}</p>
          <div
            class="mb-2"
            style="font-size: 1.5em;"
          >
            <v-icon class="mr-2">
              mdi-email
            </v-icon>
            {{ doc.data.email }}
          </div>
          <div style="font-size: 1.5em;">
            <v-icon class="mr-2">
              mdi-phone
            </v-icon>
            {{ doc.data.phone }}
          </div>
        </v-card-text>
        <v-card-actions
          v-if="publicView"
          class="justify-center"
        >
          <v-tooltip top>
            <template v-slot:activator="{ on }">
              <v-btn
                class="communicationBtn"
                fab
                dark
                color="red"
                :href="`mailto:${doc.data.email}`"
                v-on="on"
              >
                <v-icon>mdi-email</v-icon>
              </v-btn>
            </template>
            <span> {{ emailTooltip }} </span>
          </v-tooltip>


          <v-tooltip top>
            <template v-slot:activator="{ on }">
              <v-btn
                class="communicationBtn"
                fab
                dark
                color="primary"
                :href="`tel:${doc.data.phone}`"
                v-on="on"
              >
                <v-icon>mdi-phone</v-icon>
              </v-btn>
            </template>
            <span> {{ phoneTooltip }} </span>
          </v-tooltip>

          <v-tooltip top>
            <template v-slot:activator="{ on }">
              <v-btn
                class="communicationBtn"
                fab
                dark
                color="green"
                :href="`whatsapp://send?abid=${doc.data.phone}`"
                v-on="on"
              >
                <v-icon>mdi-whatsapp</v-icon>
              </v-btn>
            </template>
            <span> {{ whatsappTooltip }} </span>
          </v-tooltip>

          <v-tooltip top>
            <template v-slot:activator="{ on }">
              <v-btn
                class="communicationBtn"
                fab
                dark
                color="purple"
                :href="`viber://chat?number=%2B+381651234567`"
                v-on="on"
              >
                <v-icon>mdi-cellphone</v-icon>
              </v-btn>
            </template>
            <span> {{ viberTooltip }} </span>
          </v-tooltip>

          <v-tooltip top>
            <template v-slot:activator="{ on }">
              <v-btn
                class="communicationBtn"
                fab
                dark
                color="blue"
                :href="`https://zoom.us/j/${doc.data.zoom}`"
                target="_blank"
                v-on="on"
              >
                <v-icon>mdi-webcam</v-icon>
              </v-btn>
            </template>
            <span> {{ zoomTooltip }} </span>
          </v-tooltip>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import { Doctor } from '@/model/Doctor';
import { occupationOf } from '@/utils/data';

@Component
export default class DoctorProfile extends Vue {
  @Prop() private doc!: Doctor;

  @Prop({ type: Boolean, default: true }) private publicView!: boolean;

  private emailTooltip = 'Pošalji mail';

  private phoneTooltip = 'Pozovi';

  private whatsappTooltip = 'WhatsApp';

  private viberTooltip = 'Viber';

  private zoomTooltip = 'Zoom';

  public occupationText(doc: Doctor): string {
    return occupationOf(doc, this.publicView);
  }
}
</script>

<style>
  .communicationBtn {
    margin: 3px;
  }
</style>
