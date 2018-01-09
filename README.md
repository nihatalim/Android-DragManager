DragList is gain drag events for all View elements

There is below example in this repository in app module
![Alt Text](https://github.com/nihatalim/DragList/raw/master/GenericRecyclerWithDragListExample.gif)

Step 1: Installation

Click this button
[![](https://jitpack.io/v/nihatalim/DragList.svg)](https://jitpack.io/#nihatalim/DragList)

In the opening page, click the GET button which latest version.
Then follow the instruction.

Step 2: Usage

a) Define DragManager instance on class level like this:

```
private DragManager dragManager = null;
```

b) Then init the DragManager:

```
this.dragManager =  DragManager.init(this.target);
```

Note: this.target is a View object like TextView, RecyclerView or ImageView. The DragManager.init(View TargetView) method needs to pass target view.

c) Define a listener like onlongclicklistener or ontouch etc. for another view which you want to drag. Then add to 
overrided method this line:

```
dragManager.drag(view,null);
```

DragManager.drag(View baseView, Object data) if you want to pass data from baseView to targetView you can change null to object instance.

```
this.base.setOnLongClickListener(new View.OnLongClickListener() {
	@Override
	public boolean onLongClick(View view) {
		return dragManager.drag(view,null);
	}
});
```

d) You can define drag actions. Only 3 supported actions but it will be adding next time.

```
this.dragManager.OnDrag(new OnDrag<ViewData>(){

	@Override
	public void Drop(View view, DragEvent dragEvent, ViewData viewData) {

	}

	@Override
	public void DragEntered(View view, DragEvent dragEvent, ViewData viewData) {

	}

	@Override
	public void DragExited(View view, DragEvent dragEvent, ViewData viewData) {

	}
});

```

z) Finally you need to build your dragManager instance.

```
this.dragManager.build();
```
