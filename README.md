# Android-utils

It's a utility library extensions for android developers.

You can reduce your code using this simple extensions for manage parts of UI, Data, and Context.

if you want to collaborate by adding more extensions you can do it.

## Implementation:

Download de library and add to your project.

```

implementation project(path: ':androidutils')

```

## Examples of use:

Open dial of phone.

```

private fun openDial() {
        this.launchDial(
            phone = "5531222343" 
        )
    }

```

open email app with data.

```

private fun openEmail() {
        this.launchEmail(
            subject = "Example subject",
            body = "This is an example",
            email = "example@example.com"
        )
    }

```

## Shared preference delegation use:

Example using object:

```
class User {

    // For delegation preference
    var name: String by Delegate( SharedPreference of your project , /** default value**/ )
    var age: Int by Delegate( SharedPreference of your project , /** default value**/ )
    
}
```

Using object for modify data:

```

  val user = User()
  user.name = "Ivan" // modify de value in preferences 
  user.age = 25
  
  Log.d("TEST", user.name) // print "Ivan"

```
