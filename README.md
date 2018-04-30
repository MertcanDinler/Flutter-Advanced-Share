# Advanced Share Plugin
**EXPERIMENTAL**
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

its required one of **msg** or **url** parameters.
if you using share local file, **you need storage permissions**.

------------


| Parameter  | Description  |
| :------------ | :------------ |
| String msg  | Text message  |
| String url  | Base64 file url or Local file url  |
| String title  | Chooser title default "Share" (Optional)  |
| String subject  | For example mail subject (Optional)  |
| String type  | Intent type (Optional)  |

###### Examples
``` dart
AdvancedShare.generic(
    msg: "Its good.", 
    title: "Share with Advanced Share",
  );
```
``` dart
String BASE64_IMAGE = "data:image/png;base64, ...";
AdvancedShare.generic(
    msg: "Base64 file share", 
    subject: "Flutter",
    title: "Share Image",
    url: BASE64_IMAGE
);
```
``` dart
AdvancedShare.generic(
    url: "file:///storage/emulated/0/Download/test.txt"
);
```

