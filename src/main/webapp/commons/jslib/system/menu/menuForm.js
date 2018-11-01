Ext.onReady(function(){
	var baseUrl = Ext.fly("basePathUrl").getValue(false);
	var menuId = Ext.fly("menuId").getValue(false);
	var viewFlag = Ext.fly("viewFlag").getValue(false);
	var menuSaveFormPanel = 'menuSaveFormPanel';
	var menuSaveForm = 'menuSaveForm';
	var parentMenuComboId = 'parentMenuComboId';
	var subUrl = null;
	if(Ext.isEmpty(menuId) || menuId == "null"){
		subUrl = baseUrl+"/system/menuAction.do?method=addMenuSave";
	}else{
		subUrl = baseUrl+"/system/menuAction.do?method=editMenuSave";
	}
	
	Ext.define('MenuCombox', {
	    extend: 'Ext.data.Model',
	    fields: ['menuId','menuName', 'menuUrl','menuType','parentMenu']
	});

	var menuComboxStore = Ext.create('Ext.data.Store', {
	    storeId:'menuComboxStoreId',
	    //分页大小 
        pageSize: 20, 
        model: 'MenuCombox', 
        //是否在服务端排序 
        //remoteSort: true,
	    proxy: {
	        //异步获取数据，这里的URL可以改为任何动态页面，只要返回JSON数据即可 
            type: 'ajax', 
            url: baseUrl+'/system/menuAction.do?method=getMenuListJsonData', 
            extraParams:{'menuType':'1'}, 
            reader: { 
                root: 'root', 
                totalProperty:'count' 
            }, 
            simpleSortMode: true 
	    },
	    autoLoad: true 
	});
	
	Ext.create("Ext.container.Viewport",{
		id:menuSaveFormPanel,
		items:[{
				id:menuSaveForm,
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
                	title : '菜单信息',
					items:[{
						xtype : 'hiddenfield',
						name : 'menuId',
						value:''
						},{
						xtype : 'textfield',
						name : 'menuName',
						fieldLabel : '菜单名称',
						labelAlign : 'right',
						anchor    : '100%',
						// 表单项非空
						allowBlank : false
						},{
						xtype : 'textfield',
						name : 'menuUrl',
						fieldLabel : '菜单路径',
						labelAlign : 'right',
						anchor    : '100%',
						// 表单项非空
						allowBlank : true
						},{
                        xtype: 'radiogroup',
                        //columns: [100,100],
                        fieldLabel: '菜单类型',
                        labelAlign : 'right',
						anchor    : '100%',
                        items: [{
                        	boxLabel: '叶子', name: 'menuType',inputValue:'TREELEAF',checked: true},
                            {boxLabel: '节点', name: 'menuType',inputValue:'TREENODE'}]
                    	},{
                    	id:parentMenuComboId,
						xtype:'combo',
						name:'parentMenuId',
					    fieldLabel: '父菜单',
					    store: menuComboxStore,
					    queryMode: 'remote',
					    displayField: 'menuName',
					    valueField: 'menuId',
					    labelAlign : 'right',
						anchor    : '100%'
					},{
						xtype : 'textfield',
						name : 'icon',
						fieldLabel : '菜单图标',
						labelAlign : 'right',
						anchor    : '100%',
						// 表单项非空
						allowBlank : false
						},{
						xtype : 'numberfield',
						name : 'orderNum',
						fieldLabel : '排序号',
						labelAlign : 'right',
						anchor    : '100%'
					}]
				}]
		}]
	});
	if(!Ext.isEmpty(menuId) && menuId != "null"){
		var menuForm = Ext.getCmp(menuSaveForm).getForm();
		menuForm.load({
		    url: baseUrl+'/system/menuAction.do?method=ajaxFindMenuById',
		    params: {
		        menuId: menuId
		    },success: function(form, action) {
		    	if(!Ext.isEmpty(action.result.data.parentMenu)){
		    		Ext.getCmp(parentMenuComboId).setValue(action.result.data.parentMenu.menuId);
		    	}
		    },failure: function(form, action) {
		        window.parent.Ext.Msg.alert("加载失败", "请刷新页面重试");
		    }
		});
		if(!Ext.isEmpty(viewFlag) && viewFlag != "null"){
			if(new Boolean(viewFlag)){
				var fields = menuForm.getFields();
				for(var i = 0; i < fields.getCount();i++){
					var field = fields.getAt(i);
					field.setReadOnly(true);
				}
			}
		}
		//userForm.disable();
	}
});