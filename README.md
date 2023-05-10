# FileManagerTestTask

# Functinal
- FILES FRAGMENT
  - can open file by clicking or open folder
  - can share file by long click
  - can sort by clicking on floating button and see sorting menu
- LAST CHANGED FILES
  - here you can see progress: app using workManger will fetch all files from device exept folders, read them and generate md5 hash from content,
    compare hash with saved hash of this file from database. This operation may take time, depending 
    on amount of files on your device. In my case it took 2-3 min with 20K files. After this opertion you will see list of modified
    files since last session or message "Nothin found".

Stack:
- Kotlin
- XML
- Room
- Hil
- Corutines
- State and shared flow
- WorkManager
- NavComponent
- Codec (for hashing)

## ScreenShots:
![photo1683745610 (2)](https://github.com/BoikoIlya/FileManagerTestTask/assets/100340546/0f5bef60-70b7-47bd-9020-ea29c99a2bbc)
![photo1683745610](https://github.com/BoikoIlya/FileManagerTestTask/assets/100340546/0cc0f9b8-60cd-4447-b9fd-d958e4baf60e)
![photo1683745610 (3)](https://github.com/BoikoIlya/FileManagerTestTask/assets/100340546/cfd071b9-887d-47b9-a7f4-57b1c9ebc2d9)
![photo1683745610 (1)](https://github.com/BoikoIlya/FileManagerTestTask/assets/100340546/9556ad0f-789d-4033-a714-0b0b94b49648)
