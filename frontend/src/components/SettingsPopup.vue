<template>
  <v-row class="setting">
    <v-dialog
      v-model="dialog"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
      content-class="my-dialog-content"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="#C5CAE9"
          width="10%"
          v-bind="attrs"
          v-on="on"
        >
          설정창
          <!-- <img src="../assets/bubble.png" alt="bubble" class="bubble-image"> -->
        </v-btn>
      </template>
      <v-card>
        <v-toolbar
          dark
          color="#C5CAE9"
        >
          <v-btn
            icon
            dark
            @click="dialog = false"
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>Settings</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <v-btn
              dark
              text
              @click="dialog = false"
            >
              Save
            </v-btn>
          </v-toolbar-items>
        </v-toolbar>
        <v-list
          three-line
          subheader
        >
          <v-subheader>User Controls</v-subheader>
          
        </v-list>
        <v-divider>
          ㅇㅇ
        </v-divider>
        <v-list
          three-line
          subheader
        >
          <v-subheader>닉네임 바꾸기</v-subheader>
          <v-subheader>테마..? 배경화면 or 비눗방울 여러개</v-subheader>
          <v-subheader>로그아웃..? 탈퇴?</v-subheader>
          <v-subheader>크레딧 하나 넣고</v-subheader>
          
        </v-list>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
export default {
  name: 'SettingsPopupView',
  data() {
    return {
      dialog: false
    };
  },
  methods: {
    closeDialog() {
      this.dialog = false;
    }
  }
}
</script>

<style>
.my-dialog-content {
  
}
.bubble-image {
  height: 10%;
  width: 10%;
}
.setting {
  margin:2%
}
</style>