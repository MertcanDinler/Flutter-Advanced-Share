# Advanced Share Plugin
Share text and file your flutter app.

#### Compatible
Only android because i dont have a Mac. Can you give me a mac for gift :)

#### Usage
add `advanced_share` as a dependency in your pubspec.yaml file.

Import the library via
``` dart
import 'package:advanced_share/advanced_share.dart';
```
#### Methods
##### generic({String msg, String url, String title, String subject, String type})
##### whatsapp({String msg, String url})
##### gmail({String subject, String msg, String url})
**Result values**

| Value  | Result  |
| :------------ | :------------ |
| 0  | Failed  |
| 1  | Success  |
| 2  |  {App} isnt installed. |
| 3  | dont know :smile:  |

if you using share local file, **you need storage permissions**.

------------


| Parameter  | Description  |
| :------------ | :------------ |
| String msg  | Text message  |
| String url  | Base64 file url or Local file url  |
| String title  | Chooser title default "Share" |
| String subject  | For example mail subject  |
| String type  | Intent type  |

###### Examples
``` dart
AdvancedShare.generic(
    msg: "Its good.", 
    title: "Share with Advanced Share",
  ).then((response){
	print(response);
});
```
``` dart
String BASE64_IMAGE = "data:image/png;base64, ...";
AdvancedShare.generic(
    msg: "Base64 file share", 
    subject: "Flutter",
    title: "Share Image",
    url: BASE64_IMAGE
	).then((response){
	print(response);
	});
```
``` dart
AdvancedShare.generic(
    url: "file:///storage/emulated/0/Download/test.txt"
);
```
``` dart
    AdvancedShare.whatsapp(msg: "It's okay :)")
	.then((response) {
      handleResponse(response, appName: "Whatsapp");
    });
```