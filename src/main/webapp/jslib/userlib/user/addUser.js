//Ext.onReady(function(){
//	Ext.MessageBox.alert("Hello","Hello ExtJS4!");
//	new Ext.Viewport({
//		layout:"fit",
//		items:[{
//			xtype:"panel",
//			title:"欢迎",
//			html:"<h1>Hello ExtJS4!</h1>"
//		}]
//	});
//});

Ext.onReady(function(){
	var baseUrl = Ext.fly("basePathUrl").getValue(false);
	var userId = Ext.fly("userId").getValue(false);
	var viewFlag = Ext.fly("viewFlag").getValue(false);
	var addUserSaveFormPanel = 'addUserSaveFormPanel';
	var addUserSaveForm = 'addUserSaveForm';
	var subUrl = null;
	if(Ext.isEmpty(userId) || userId == "null"){
		subUrl = baseUrl+"userAction.do?method=addUserSave";
	}else{
		subUrl = baseUrl+"userAction.do?method=editUserSave";
	}
	
	Ext.create("Ext.container.Viewport",{
		id:addUserSaveFormPanel,
		items:[{
				id:addUserSaveForm,
				xtype:'form',
				//title : "增加用户",
				url:subUrl,
				method:"post",
				standardSubmit:false,
				//height:800,
				//frame:true,
				//width:240,
				//layout:"anchor",
				//width: 350,
//				layout: {
//        			type: 'vbox',
//        			align: 'center'
//        			//pack: 'center'
//				},
				items : [{
					xtype : 'fieldset',
                	title : '用户信息',
					items:[{
						xtype : 'hiddenfield',
						name : 'userId',
						value:''
						},{
						xtype : 'textfield',
						name : 'userName',
						fieldLabel : '用户名',
						labelAlign : 'right',
						anchor    : '100%',
						// 表单项非空
						allowBlank : false
						},{
						xtype : 'textfield',
						name : 'password',
						fieldLabel : '密码',
						labelAlign : 'right',
						anchor    : '100%',
						// 表单项非空
						allowBlank : false
						},{
                        xtype: 'radiogroup',
                        //columns: [100,100],
                        fieldLabel: '性别',
                        labelAlign : 'right',
						anchor    : '100%',
                        items: [
                        	{boxLabel: '男', name: 'sex',inputValue:'MAN',checked: true},
                            {boxLabel: '女', name: 'sex',inputValue:'WOMAN'}]
                    	},{
						xtype : 'numberfield',
						name : 'age',
						fieldLabel : '年龄',
						labelAlign : 'right',
						anchor    : '100%',
						// 表单项非空
						allowBlank : false,
						maxValue : 200,
						minValue : 0,
						negativeText:'年龄不为负数'
						},{
						xtype : 'datefield',
						name : 'birthday',
						format:'Y-m-d',
						fieldLabel : '生日',
						labelAlign : 'right',
						//autoShow:true,
						anchor    : '100%',
						// 表单项非空
						allowBlank : false,
						listeners: {
							focus:{
								fn:function(){
									if(Ext.isEmpty(viewFlag) || viewFlag == "null"){
										this.expand();
									}
								}
							}
						}
						},{
						xtype : 'textfield',
						name : 'email',
						fieldLabel : '邮箱地址',
						labelAlign : 'right',
						anchor    : '100%',
						// 表单项非空
						allowBlank : false
						},{
						xtype : 'textfield',
						name : 'address',
						fieldLabel : '住址',
						labelAlign : 'right',
						//anchor:"40%",
						anchor    : '100%',
						// 表单项非空
						allowBlank : false
						},{
						xtype : 'textareafield',
						name : 'commentContext',
						fieldLabel : '备注',
						labelAlign : 'right',
						anchor    : '100%',
						// 表单项非空
						allowBlank : false
					}]
				}]
//				listeners: {
//					render:{
//						fn:function(){
//							alert();
//						}
//					}
//				}
		}]
	});
	if(!Ext.isEmpty(userId) && userId != "null"){
		var userForm = Ext.getCmp(addUserSaveForm).getForm();
		userForm.load({
		    url: baseUrl+'userAction.do?method=ajaxFindUserById',
		    params: {
		        userId: userId
		    },success: function(form, action) {
		        //alert();
		    },failure: function(form, action) {
		        window.parent.Ext.Msg.alert("加载失败", "请刷新页面重试");
		    }
		});
		if(!Ext.isEmpty(viewFlag) && viewFlag != "null"){
			if(new Boolean(viewFlag)){
				var fields = userForm.getFields();
				for(var i = 0; i < fields.getCount();i++){
					var field = fields.getAt(i);
					field.setReadOnly(true);
				}
			}
		}
		//userForm.disable();
	}
});