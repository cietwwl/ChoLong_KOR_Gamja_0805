package l1j.server.server.model.Instance;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.ADVANCE_SPIRIT;
import static l1j.server.server.model.skill.L1SkillId.BLESS_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.BONE_BREAK;
import static l1j.server.server.model.skill.L1SkillId.CURSE_PARALYZE;
import static l1j.server.server.model.skill.L1SkillId.CURSE_PARALYZE2;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.FOG_OF_SLEEPING;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.IRON_SKIN;
import static l1j.server.server.model.skill.L1SkillId.MOB_BASILL;
import static l1j.server.server.model.skill.L1SkillId.MOB_COCA;
import static l1j.server.server.model.skill.L1SkillId.MOB_RANGESTUN_18;
import static l1j.server.server.model.skill.L1SkillId.MOB_RANGESTUN_19;
import static l1j.server.server.model.skill.L1SkillId.MOB_SHOCKSTUN_30;
import static l1j.server.server.model.skill.L1SkillId.NATURES_TOUCH;
import static l1j.server.server.model.skill.L1SkillId.PHANTASM;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_DEX;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_STR;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_STUN;
import static l1j.server.server.model.skill.L1SkillId.STATUS_COMA_5;

import java.awt.Robot;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.SpecialEventHandler;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.IndunSystem.MiniGame.BattleZone;
import l1j.server.Warehouse.ClanWarehouse;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.ActionCodes;
import l1j.server.server.GMCommands;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.Opcodes;
import l1j.server.server.PacketOutput;
import l1j.server.server.SkillCheck;
import l1j.server.server.TimeController.FishingTimeController;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.clientpackets.C_SabuTeleport;
import l1j.server.server.clientpackets.C_SelectCharacter;
import l1j.server.server.command.executor.L1HpBar;
import l1j.server.server.datatables.CharBuffTable;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.IpPhoneCertificationTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.MonsterBookTeleportTable;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.model.AHRegeneration;
import l1j.server.server.model.AcceleratorChecker;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.HalloweenArmorBlessing;
import l1j.server.server.model.HalloweenRegeneration;
import l1j.server.server.model.HpRegenerationByDoll;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1ChatParty;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1EquipmentSlot;
import l1j.server.server.model.L1ExcludingLetterList;
import l1j.server.server.model.L1ExcludingList;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Karma;
import l1j.server.server.model.L1NameList;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Party;
import l1j.server.server.model.L1PartyRefresh;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1PinkName;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Quest;
import l1j.server.server.model.L1StatReset;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1TownLocation;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.L1�丶ȣũ;
import l1j.server.server.model.LindBlessing;
import l1j.server.server.model.MpDecreaseByScales;
import l1j.server.server.model.MpRegenerationByDoll;
import l1j.server.server.model.PapuBlessing;
import l1j.server.server.model.SHRegeneration;
import l1j.server.server.model.classes.L1ClassFeature;
import l1j.server.server.model.gametime.GameTimeCarrier;
import l1j.server.server.model.monitor.L1PcAutoUpdate;
import l1j.server.server.model.monitor.L1PcExpMonitor;
import l1j.server.server.model.monitor.L1PcGhostMonitor;
import l1j.server.server.model.monitor.L1PcHellMonitor;
import l1j.server.server.model.monitor.L1PcInvisDelay;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_BlueMessage;
import l1j.server.server.serverpackets.S_CastleMaster;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_CharTitle;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_ClanJoinLeaveStatus;
import l1j.server.server.serverpackets.S_DRAGONPERL;
import l1j.server.server.serverpackets.S_Dexup;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_Fishing;
import l1j.server.server.serverpackets.S_HPMeter;
import l1j.server.server.serverpackets.S_HPUpdate;
import l1j.server.server.serverpackets.S_Invis;
import l1j.server.server.serverpackets.S_Lawful;
import l1j.server.server.serverpackets.S_Liquor;
import l1j.server.server.serverpackets.S_MPUpdate;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_NewUI;
import l1j.server.server.serverpackets.S_OtherCharPacks;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_PetWindow;
import l1j.server.server.serverpackets.S_PinkName;
import l1j.server.server.serverpackets.S_Poison;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_ReturnedStat;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_Strup;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_UseArrowSkill;
import l1j.server.server.serverpackets.S_bonusstats;
import l1j.server.server.serverpackets.S_�����ֽ�;
import l1j.server.server.serverpackets.ServerBasePacket;
import l1j.server.server.templates.L1BookMark;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1PrivateShopBuyList;
import l1j.server.server.templates.L1PrivateShopSellList;
import l1j.server.server.types.Point;
import l1j.server.server.utils.CalcStat;
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;
import server.message.ServerMessage;

public class L1PcInstance extends L1Character {

	
	private final L1NameList _nameList = new L1NameList();

	public L1NameList getNameList() {
		return _nameList;
	}
	
	public int _Ʈ��Ÿ�� = 0;
	public int getƮ��Ÿ��() { return _Ʈ��Ÿ��; }
	public void setƮ��Ÿ��(int i) { _Ʈ��Ÿ�� = i; }
	// ����pc ���� flag
	public boolean noPlayerCK = false;
	
	//
	private String diement = null;
	private static String[] _diementArray = { "�����׳�" , "�Ѥ�", "���̤���", "������������","","�����䤻",""};
	
	public boolean ��ؽ� = false;
	public boolean ������¡ = false;
	public int ����Ʈ = 0;
	public int �׷��̽��ƹ�Ÿ = 0;
	private int ���巹��;
	  /** ��Ʋ�� **/
    private int _DuelLine;

    public int get_DuelLine() {
        return _DuelLine;
    }

    public void set_DuelLine(int i) {
        _DuelLine = i;
    }
	public int get���巹��() {
		return ���巹��;
	}
	public void set���巹��(int i) {
		���巹�� = i;
	}
	
	
	private boolean _isRobot = false;
	
	public boolean isRobot() {
		return _isRobot;
	}

	public void setRobot(boolean flag) {
		_isRobot = flag;
	}
	private String _chatTarget = "";

	public String getChatTarget() {
		return _chatTarget;
	}

	public void setChatTarget(String _chatTarget) {
		this._chatTarget = _chatTarget;
	}

	public boolean ���� = true;
	
	public boolean �����ֽ� = false;

	public int ������󵵰����ڷ��� = 0;

	public boolean ĳ�������� = false;

	public L1PcInstance _healagro = null;

	public L1PcInstance _�̹þ�׷� = null;

	public long window_active_time = -1;

	public int window_noactive_count = 0;

	public boolean �ϵ򺸽������� = false;

	public boolean �δ������� = false;
	public int fouradddmg = 0;
	public int testobj = 0;
	public boolean restart = false;
	private static final long serialVersionUID = 1L;
	public int _npcnum = 0;
	public long _attacktime = 0;
	public long _skilltime = 0;
	public long _attacktime2 = 0;
	public String _npcname = "";
	public String _note = "";
	public boolean aincheck = false;
	public long speed_time_temp = 0;
	public boolean ũ���� = false;
	public boolean �翤 = false;
	public boolean ���� = false;
	public int �뺴Ÿ�� = 0;
	public int ��Ű���üũid = 0;
	public int talkingNpcObjid = 0;
	public int createItemNpcObjid = 0;
	public byte[] ������ = new byte[512];
	int _giganmp = 0;
	int _giganhp = 0;

	public boolean ������üũ�� = false;
	public L1�丶ȣũ �丶ȣũth = null;

	public String EncObjid = "";

	public boolean ������ = false;

	public int getggHp() {
		return _giganhp;
	}

	public void setggHp(int i) {
		_giganhp = i;
	}

	public int getggMp() {
		return _giganmp;
	}

	public void setggMp(int i) {
		_giganmp = i;
	}

	private PacketOutput _out = null;

	public void setPacketOutput(PacketOutput out) {
		_out = out;
	}
	

	/** ���� �������� �˻��Ѵ� ���� **/



	public boolean skillCritical = false;
	public boolean skillismiss = false;
	public int _PlayEXP = 0;
	public byte _PlayLevel = 0;
	public int _PlayMonKill = 0;
	public int _PlayPcKill = 0;
	public int _PlayAden = 0;
	public int _PlayItem = 0;
	public String _PlayTime = null;
	public short _���������ܰ��� = 0;
	public int �Ƚþ����ۻ��id = 0;
	public int ���������ۻ��id = 0;
	public int ����80���ž����ۻ��id = 0;
	public int �ҷ���ȣ�ھ�Time = 0;
	public int �����̺�ƮTime = 0;
	//�޺�
    private int comboCount;

	public int �׷����̺�ƮTime = 0;
	public int �����ָ��̺�ƮTime = 0;

	public int ���ڻ���Time = 0;
	public byte ���̺���Count = 0;
	public byte ��������_���ڿ���_Count = 0;

	public byte ��������_�׷���_Count = 0;

	public byte ��������_�ҷ��������_Count = 0;

	public String ���޴�ȭâ = "";
	public boolean war_zone = false;
	public boolean ����_�ü�_�� = false;
	public boolean PC��_���� = false;
	public boolean PC��_���������� = false;
	public boolean ����Load = false;
	public boolean ��������Load = false;

	public int tt_clanid = -1;
	public int tt_partyid = -1;
	public int tt_level = 0;

	public boolean TownMapTeleporting = false;

	public long ANTI_ERUPTION = 0;
	public long ANTI_METEOR_STRIKE = 0;
	public long ANTI_SUNBURST = 0;
	public long ANTI_CALL_LIGHTNING = 0;
	public long ANTI_DISINTEGRATE = 0;
	public long ANTI_BONE_BREAK = 0;
	public long ANTI_FINAL_BURN = 0;
	public long ANTI_ICE_SPIKE = 0;

	public byte system = -1;
	public short dragonmapid;
	public static final int CLASSID_PRINCE = 0;
	public static final int CLASSID_PRINCESS = 1;
	public static final int CLASSID_KNIGHT_MALE = 61;
	public static final int CLASSID_KNIGHT_FEMALE = 48;
	public static final int CLASSID_ELF_MALE = 138;
	public static final int CLASSID_ELF_FEMALE = 37;
	public static final int CLASSID_WIZARD_MALE = 734;
	public static final int CLASSID_WIZARD_FEMALE = 1186;
	public static final int CLASSID_DARKELF_MALE = 2786;
	public static final int CLASSID_DARKELF_FEMALE = 2796;
	public static final int CLASSID_DRAGONKNIGHT_MALE = 6658;
	public static final int CLASSID_DRAGONKNIGHT_FEMALE = 6661;
	public static final int CLASSID_ILLUSIONIST_MALE = 6671;
	public static final int CLASSID_ILLUSIONIST_FEMALE = 6650;
	public static final int CLASSID_WARRIOR_MALE = 12490;
	public static final int CLASSID_WARRIOR_FEMALE = 12494;

	public static final int REGENSTATE_NONE = 12;
	public static final int REGENSTATE_MOVE = 6;
	public static final int REGENSTATE_ATTACK = 3;
	

    public static final int SPAWN_GIRTAS = 1;

	public boolean isCrash = false;
	public boolean isPurry = false;
	public boolean isSlayer = false;
	public boolean isAmorGaurd = false;
	public boolean isTaitanR = false;
	public boolean isTaitanB = false;
	public boolean isTaitanM = false;
	
	public boolean _robot = true;

	public boolean ���͹��� = false;
	public int ����ã��Objid = 0;
	public int ���������� = 0;
	public boolean ����� = false;
	public int ��Ż������ = 0;
	public int �̽� = 0;
	public int Ÿ�� = 0;
	public int ���� = 0;
	private L1ClassFeature _classFeature = null;
	private L1EquipmentSlot _equipSlot;
	private String _accountName;
	private short _classId;
	private short _type;
	private int _exp;
	private short _accessLevel;

	private int _age; // ���� by ��ī
	private int _AddRingSlotLevel;

	public int getRingSlotLevel() {
		return _AddRingSlotLevel;
	}

	public void setRingSlotLevel(int i) {
		_AddRingSlotLevel = i;
	}

	private int _AddEarringSlotLevel;

	public int getEarringSlotLevel() {
		return _AddEarringSlotLevel;
	}

	public void setEarringSlotLevel(int i) {
		_AddEarringSlotLevel = i;
	}

	/** ���� **/
	private int birthday;
	private boolean _FirstBlood;
	private int _TelType = 0;

	public int getTelType() {
		return _TelType;
	}

	public void setTelType(int i) {
		_TelType = i;
	}

	public int tempx = 0;
	public int tempy = 0;
	public short tempm = 0;
	public int temph = 0;

	public int dx = 0;
	public int dy = 0;
	public short dm = 0;
	public short fdmap = 0;
	public int dh = 0;

	public void ��ܰ���() {// 13283
		/*
		 * if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { //
		 * ���������� ���� pc.sendPackets(new S_ServerMessage(698), true); return; }
		 */
		if (getSkillEffectTimerSet()
				.hasSkillEffect(L1SkillId.STATUS_DRAGONPERL)) {
			getSkillEffectTimerSet().killSkillEffectTimer(
					L1SkillId.STATUS_DRAGONPERL);
			sendPackets(new S_PacketBox(S_PacketBox.DRAGONPERL, 0, 0), true);
			Broadcaster.broadcastPacket(this, new S_DRAGONPERL(getId(), 0),
					true);
			sendPackets(new S_DRAGONPERL(getId(), 0), true);
			set���ּӵ�(0);
		}
		cancelAbsoluteBarrier();// �ۼ�����(�ѿ� �� �޼ҵ������ ����)
		int time = 600 * 1000;
		int stime = (int) (((time / 1000) / 1)); //���� �ð�
		getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_DRAGONPERL,
				time);
		sendPackets(new S_SkillSound(getId(), 13283), true);// ������ ����Ʈ...
		Broadcaster.broadcastPacket(this, new S_SkillSound(getId(), 13283),
				true);
		sendPackets(new S_PacketBox(S_PacketBox.DRAGONPERL, 8, stime), true);
		sendPackets(new S_DRAGONPERL(getId(), 8), true);
		Broadcaster.broadcastPacket(this, new S_DRAGONPERL(getId(), 8), true);
		set���ּӵ�(1);

	}

	public void �ʱ�ȭ() {
		tempstr = 0;
		tempdex = 0;
		tempcon = 0;
		tempwis = 0;
		tempcha = 0;
		tempint = 0;

		tempstr2 = 0;
		tempdex2 = 0;
		tempcon2 = 0;
		tempwis2 = 0;
		tempcha2 = 0;
		tempint2 = 0;
	}

	int _oldzone = 255;

	public void Stat_Reset_All() {
		resetBaseAc();
		resetBaseMr();
		// setBaseMagicDecreaseMp(CalcStat.���Ҹ𰨼�(getAbility().getTotalInt()));
		setBaseMagicHitUp(CalcStat.��������(getAbility().getTotalInt()));
		// setBaseMagicCritical(CalcStat.����ġ��Ÿ(getAbility().getTotalInt()));
		// setBaseMagicDmg(CalcStat.���������(getAbility().getTotalInt()));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalStr(),
				getAbility().getTotalCon(), "��", getType(), this));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalDex(),
				0, "��", getType(), this));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalCon(),
				getAbility().getTotalStr(), "��", getType(), this));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalInt(),
				0, "��Ʈ", getType(), this));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalWis(),
				0, "����", getType(), this));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalCha(),
				0, "ī��", getType(), this));
		sendPackets(new S_NewCreateItem(0x01ea, "��������", this));
		sendPackets(new S_NewCreateItem("����", this));
		sendPackets(new S_SPMR(this));

		if (getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.PHYSICAL_ENCHANT_STR)) {
			getAbility().addAddedStr((byte) 5);
			int retime = getSkillEffectTimerSet().getSkillEffectTimeSec(
					L1SkillId.PHYSICAL_ENCHANT_STR);
			sendPackets(new S_Strup(this, 5, retime), true);
			// System.out.println(getAbility().getAddedStr());
		}
		if (getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.PHYSICAL_ENCHANT_DEX)) {
			getAbility().addAddedDex((byte) 5);
			sendPackets(new S_PacketBox(S_PacketBox.char_ER, get_PlusEr()),
					true);
			int retime = getSkillEffectTimerSet().getSkillEffectTimeSec(
					L1SkillId.PHYSICAL_ENCHANT_DEX);
			sendPackets(new S_Dexup(this, 5, retime), true);
			// System.out.println(getAbility().getAddedDex());
		}

	}

	public void Stat_Reset_Str() {
		sendPackets(new S_NewCreateItem(0x01ea, "��������", this));
		sendPackets(new S_NewCreateItem("����", this));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalStr(),
				getAbility().getTotalCon(), "��", getType(), this));
	}

	public void Stat_Reset_Dex() {
		sendPackets(new S_NewCreateItem(0x01ea, "��������", this));
		resetBaseAc();
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalDex(),
				0, "��", getType(), this));
	}

	public void Stat_Reset_Con() {
		sendPackets(new S_NewCreateItem(0x01ea, "��������", this));
		sendPackets(new S_NewCreateItem("����", this));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalCon(),
				getAbility().getTotalStr(), "��", getType(), this));
		sendPackets(new S_HPUpdate(this));
	}

	public void Stat_Reset_Int() {
		sendPackets(new S_NewCreateItem(0x01ea, "��������", this));
		// setBaseMagicDecreaseMp(CalcStat.���Ҹ𰨼�(getAbility().getTotalInt()));
		setBaseMagicHitUp(CalcStat.��������(getAbility().getTotalInt()));
		// setBaseMagicCritical(CalcStat.����ġ��Ÿ(getAbility().getTotalInt()));
		// setBaseMagicDmg(CalcStat.���������(getAbility().getTotalInt()));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalInt(),
				0, "��Ʈ", getType(), this));
	}

	public void Stat_Reset_Wis() {
		sendPackets(new S_NewCreateItem(0x01ea, "��������", this));
		resetBaseMr();
		sendPackets(new S_SPMR(this));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalWis(),
				0, "����", getType(), this));
	}

	public void Stat_Reset_Cha() {
		sendPackets(new S_NewCreateItem(0x01ea, "��������", this));
		sendPackets(new S_NewCreateItem(0x01e3, 1, getAbility().getTotalCha(),
				0, "ī��", getType(), this));
	}

	private static final int[] allBuffComaSkill = {
			L1SkillId.HASTE,
			ADVANCE_SPIRIT,
			// FIRE_WEAPON,
			BLESS_WEAPON, NATURES_TOUCH, L1SkillId.AQUA_PROTECTER, IRON_SKIN,
			L1SkillId.SHINING_AURA, L1SkillId.CONCENTRATION,
			L1SkillId.PATIENCE, L1SkillId.INSIGHT,
			7895,
			L1SkillId.REMOVE_CURSE,
			// L1SkillId.IMMUNE_TO_HARM,
			L1SkillId.IllUSION_OGRE, L1SkillId.IllUSION_DIAMONDGOLEM,
			PHYSICAL_ENCHANT_DEX, PHYSICAL_ENCHANT_STR,
			L1SkillId.FEATHER_BUFF_C, STATUS_COMA_5 };// 10528,

	class ȭ����� extends TimerTask {
		public ȭ�����() {
		}

		public void run() {
			L1SkillUse l1skilluse = null;
			for (int i = 0; i < allBuffComaSkill.length; i++) {
				if (allBuffComaSkill[i] == 7895) {
					getSkillEffectTimerSet().removeSkillEffect(7893);
					getSkillEffectTimerSet().removeSkillEffect(7894);
					getSkillEffectTimerSet().removeSkillEffect(7895);
					addDmgup(3);
					addHitup(3);
					getAbility().addSp(3);
					sendPackets(new S_SPMR(L1PcInstance.this));
					getSkillEffectTimerSet().setSkillEffect(7895, 1800 * 1000);
				} else if (allBuffComaSkill[i] == 10528) {
					if (!getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.����Ǳ��)) {
						getAC().addAc(-2);
						addMaxHp(20);
						addMaxMp(13);
						getResistance().addBlind(10);
						sendPackets(new S_HPUpdate(getCurrentHp(), getMaxHp()));
						sendPackets(new S_MPUpdate(getCurrentMp(), getMaxMp()));
						sendPackets(new S_OwnCharStatus(L1PcInstance.this));
					}
					L1PcInstance.this.sendPackets(new S_SkillSound(getId(),
							4914), true);
					Broadcaster.broadcastPacket(L1PcInstance.this,
							new S_SkillSound(getId(), 4914));
					getSkillEffectTimerSet().setSkillEffect(L1SkillId.����Ǳ��,
							1800 * 1000);
				} else {
					l1skilluse = new L1SkillUse();
					l1skilluse.handleCommands(L1PcInstance.this,
							allBuffComaSkill[i], getId(), getX(), getY(), null,
							0, L1SkillUse.TYPE_GMBUFF);
				}
				try {
					Thread.sleep(500L);
				} catch (Exception e) {
				}
			}
		}
	}

	public void ȭ�����start() {
		Timer timer = new Timer();
		timer.schedule(new ȭ�����(), 500);// 30�ʾȿ� �α��ξ����� ����
		// GeneralThreadPool.getInstance().schedule(new ȭ�����(), 500);
	}

	public void ����г�Ƽ(boolean login) {
		if (login || _oldzone != CharPosUtil.getZoneType(this)) {
			if (CharPosUtil.getZoneType(this) == 1) {
				sendPackets(new S_NewCreateItem(S_NewCreateItem.����г�Ƽ, true),
						true); // ����г�Ƽ?
			} else {
				sendPackets(new S_NewCreateItem(S_NewCreateItem.����г�Ƽ, false),
						true); // ����г�Ƽ?
			}

			if (getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.EXP_POTION_cash)) {
				int time = getSkillEffectTimerSet().getSkillEffectTimeSec(
						L1SkillId.EXP_POTION_cash);
				sendPackets(new S_PacketBox(this, time, true, true, true));
			}

			if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION)) {
				int time = getSkillEffectTimerSet().getSkillEffectTimeSec(
						L1SkillId.EXP_POTION);
				sendPackets(new S_PacketBox(this, time, true, true, true));
			}

		}
		_oldzone = CharPosUtil.getZoneType(this);
	}

	@SuppressWarnings("deprecation")
	public int �����ð�üũ(String s) {
		int maxtime = 0;
		int time = 0;
		Timestamp LastINDay = null;
		if (s.equals("�Ⱘ")) {
			maxtime = 60 * 60 * 3;
			time = getgirantime();
			LastINDay = getgiranday();
		} else if (s.equals("���ž")) {
			maxtime = 3600;
			time = getivorytime();
			LastINDay = getivoryday();
		} else 
	 if (s.equals("����")) {
		maxtime = 1800;
		time = getpc����time();
		LastINDay = getpc����day();
	 } else
			if (s.equals("���")) {
			maxtime = 10800;
			time = getpc���time();
			LastINDay = getpc���day();
		}
		if (maxtime != 0) {
			Timestamp nowday = new Timestamp(System.currentTimeMillis());
			if (LastINDay != null) {
				long clac = nowday.getTime() - LastINDay.getTime();

				int hours = nowday.getHours();
				int lasthours = LastINDay.getHours();

				if (LastINDay.getDate() != nowday.getDate()) {
					if (clac > 86400000 || hours >= Config.D_Reset_Time
							|| lasthours < Config.D_Reset_Time) {// 24�ð��� �����ų�
																	// ����9�����Ķ��
						return maxtime;
					}
				} else {
					if (lasthours < Config.D_Reset_Time
							&& hours >= Config.D_Reset_Time) {// ������ 9��������
																// ����üũ
						return maxtime;
					}
				}

				if (maxtime <= time) {
					return 0;// ��λ��
				} else {
					return maxtime - time;
				}
			} else {
				return maxtime;
			}
		}
		return 0;
	}

	public byte tempstr = 0;
	public byte tempdex = 0;
	public byte tempcon = 0;
	public byte tempwis = 0;
	public byte tempcha = 0;
	public byte tempint = 0;

	public byte tempstr2 = 0;
	public byte tempdex2 = 0;
	public byte tempcon2 = 0;
	public byte tempwis2 = 0;
	public byte tempcha2 = 0;
	public byte tempint2 = 0;

	private int checktime;

	private Timestamp antatime;

	private Timestamp lindtime;

	private Timestamp papootime;

	private Timestamp DEtime;

	private Timestamp DEtime2;

	/** �ڸ� ������ ���� by ���� **/
	public byte c_dm = 0;
	public byte c_gh = 0;
	public byte c_pr = 0;
	public byte c_pm = 0;
	public byte c_md = 0;

	public void coma_reset() {
		c_dm = 0;
		c_gh = 0;
		c_pr = 0;
		c_pm = 0;
		c_md = 0;
	}

	public int ChainSwordObjid = 0;

	private boolean _isPOP = false;// �����ּ� l1ttack

	public void setPOP(boolean flag) {
		this._isPOP = flag;
	}

	public boolean getPOP() {
		return _isPOP;
	}

	private int _baseMaxHp = 0;
	private int _baseMaxMp = 0;
	private int _baseAc = 0;

	private int _baseBowDmgup = 0;
	private int _baseDmgup = 0;
	private int _baseHitup = 0;
	private int _baseBowHitup = 0;

	private int _baseMagicHitup = 0; // ���̽� ���ȿ� ���� ���� ����
	private int _baseMagicCritical = 0; // ���̽� ���ȿ� ���� ���� ġ��Ÿ(%)
	private int _baseMagicDmg = 0; // ���̽� ���ȿ� ���� ���� ������
	private int _baseMagicDecreaseMp = 0; // ���̽� ���ȿ� ���� ���� ������

	private int _HitupByArmor = 0; // ���� �ⱸ�� ���� �������� ������
	private int _bowHitupByArmor = 0; // ���� �ⱸ�� ���� Ȱ�� ������
	private int _DmgupByArmor = 0; // ���� �ⱸ�� ���� �������� ��Ÿ��
	private int _bowDmgupByArmor = 0; // ���� �ⱸ�� ���� Ȱ�� ��Ÿ��

	private int _bowHitupBydoll = 0; // ������ ���� ���Ÿ� ���ݼ�����
	private int _bowDmgupBydoll = 0; // ������ ���� ���Ÿ� ��Ÿ��

	private int _PKcount;

	/*
	 * private int _autoct; //by��� ���������߰�
	 * 
	 * private int _autogo; //by��� ���������߰�
	 * 
	 * private String _autocode; //by��� ���������߰�
	 * 
	 * private int _autook; //by��� ���������߰�
	 */

	private long _SurvivalCry;

	public int getNcoin() {
		if (getNetConnection() != null) {
			if (getNetConnection().getAccount() != null) {
				return getNetConnection().getAccount().Ncoin_point;
			}
		}
		return 0;
	}

	public void addNcoin(int coin) {
		if (getNetConnection() != null) {
			if (getNetConnection().getAccount() != null) {
				getNetConnection().getAccount().Ncoin_point += coin;
			}
		}
	}

	/**** ���������̿� ***/
	private byte _nbapoLevel;
	private byte _obapoLevel;
	/**** ���������̿� ***/

	/**** ������Ÿ�� ***/
	private byte _bapodmg;
	/**** ������Ÿ�� ***/
	private int _�����Ƶ�����;
	// private int _�����Ƶ�����;
	public int ����üũ�ð�;

	public int get�Ƶ��˻�() {
		return _�����Ƶ�����;
	}

	public void ������ġ����(int lv) {
		int needExp = ExpTable.getNeedExpNextLevel(lv);
		int addexp = 0;
		addexp = (int) (needExp * 0.01);
		if (addexp != 0) {
			int level = ExpTable.getLevelByExp(getExp() + addexp);
			if (level > Config.MAXLEVEL) {
				sendPackets(new S_SystemMessage("���̻� ����ġ�� ȹ�� �� �� �����ϴ�."));
			} else {
				addExp(addexp);
			}
		}
	}

	public void ����������(int id) {
		int count = fairlycount(getId());
		������[id] = 1;
		if (count == 0) {
			fairlystore(getId(), ������);
		} else {

			fairlupdate(getId(), ������);
		}
	}

	public boolean tamcheck() {
		long sysTime = System.currentTimeMillis();
		if (getTamTime() != null) {
			if (sysTime <= getTamTime().getTime()) {
				return true;
			}
		}
		return false;
	}

	public long TamTime() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Timestamp tamtime = null;
		long time = 0;
		long sysTime = System.currentTimeMillis();
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT `TamEndTime` FROM `characters` WHERE account_name = ? ORDER BY `TamEndTime` ASC"); // �ɸ���
																																	// ���̺�����
																																	// ���ָ�
																																	// ���ͼ�
			pstm.setString(1, getAccountName());
			rs = pstm.executeQuery();
			while (rs.next()) {
				tamtime = rs.getTimestamp("TamEndTime");
				if (tamtime != null) {
					if (sysTime < tamtime.getTime()) {
						time = tamtime.getTime() - sysTime;
						break;
						/*
						 * temp = tamtime.getTime()-sysTime; if(time > temp ||
						 * time == 0){ time = temp; }
						 */
					}
				}
			}
			return time;
		} catch (Exception e) {
			e.printStackTrace();
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return time;
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public int tamcount() {
		Connection con = null;
		Connection con2 = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		PreparedStatement pstm2 = null;
		Timestamp tamtime = null;
		int count = 0;
		long sysTime = System.currentTimeMillis();
		int char_objid = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT * FROM `characters` WHERE account_name = ?"); // �ɸ���
																							// ���̺�����
																							// ���ָ�
																							// ���ͼ�
			pstm.setString(1, getAccountName());
			rs = pstm.executeQuery();
			while (rs.next()) {
				tamtime = rs.getTimestamp("TamEndTime");
				char_objid = rs.getInt("objid");
				if (tamtime != null) {
					if (sysTime <= tamtime.getTime()) {
						count++;
					} else {
						if (Tam_wait_count(char_objid) != 0) {
							int day = Nexttam(char_objid);
							if (day != 0) {
								Timestamp deleteTime = null;
								deleteTime = new Timestamp(sysTime
										+ (86400000 * (long) day) + 10000);// 7��
								// deleteTime = new Timestamp(sysTime +
								// 1000*60);//7��

								if (getId() == char_objid) {
									setTamTime(deleteTime);
								}
								con2 = L1DatabaseFactory.getInstance()
										.getConnection();
								pstm2 = con2
										.prepareStatement("UPDATE `characters` SET TamEndTime=? WHERE account_name = ? AND objid = ?"); // �ɸ���
																																		// ���̺�����
																																		// ���ָ�
																																		// ���ͼ�
								pstm2.setTimestamp(1, deleteTime);
								pstm2.setString(2, getAccountName());
								pstm2.setInt(3, char_objid);
								pstm2.executeUpdate();
								tamdel(char_objid);
								count++;
							}
						}
					}
				}
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return count;
		} finally {
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void tamdel(int objectId) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("delete from Tam where objid = ? order by id asc limit 1");
			pstm.setInt(1, objectId);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public String encobjid = null;
	public int ǥ�� = 0;

	public int Nexttam(int objectId) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int day = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT day FROM `tam` WHERE objid = ? order by id asc limit 1"); // �ɸ���
																										// ���̺�����
																										// ���ָ�
																										// ���ͼ�
			pstm.setInt(1, objectId);
			rs = pstm.executeQuery();
			while (rs.next()) {
				day = rs.getInt("Day");
			}
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return day;
	}

	public int Tam_wait_count(int charid) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM `tam` WHERE objid = ?");
			pstm.setInt(1, charid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				count = getId();
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return count;
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public int fairlycount(int objectId) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT count(*) as cnt FROM character_Fairly_Config WHERE object_id=?");
			pstm.setInt(1, objectId);
			rs = pstm.executeQuery();
			if (rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return result;
	}

	public void fairlystore(int objectId, byte[] data) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("INSERT INTO character_Fairly_Config SET object_id=?, data=?");
			pstm.setInt(1, objectId);
			pstm.setBytes(2, data);
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void fairlupdate(int objectId, byte[] data) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE character_Fairly_Config SET data=? WHERE object_id=?");
			pstm.setBytes(1, data);
			pstm.setInt(2, objectId);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void InvCheckTrunCate() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("TRUNCATE spr_action3");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void InvCheckStore(L1ItemInstance item) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("INSERT INTO spr_action3 SET id = ? , item_id = ?, char_id = ? , char_name = ?, item_name = ?, count = ?, enchantlvl = ?");

			pstm.setInt(1, item.getId());
			pstm.setInt(2, item.getItemId());
			pstm.setInt(3, getId());
			pstm.setString(4, getName());
			pstm.setString(5, item.getName());
			pstm.setInt(6, item.getCount());
			pstm.setInt(7, item.getEnchantLevel());

			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void ���üũ() {
		if (getAC().getAc() < -250) {
			if (Config.����ä�ø����() > 0) {
				for (L1PcInstance gm : Config.toArray����ä�ø����()) {
					if (gm.getNetConnection() == null) {
						Config.remove����(gm);
						continue;
					}
					gm.sendPackets(new S_SystemMessage("�� ���� �ǽ� " + getName()
							+ " AC : " + ac.getAc() + " �ñ� ó����"), true);
					_netConnection.close();
				}
			}
		} else if (getAC().getAc() < -250) {
			if (Config.����ä�ø����() > 0) {
				for (L1PcInstance gm : Config.toArray����ä�ø����()) {
					if (gm.getNetConnection() == null) {
						Config.remove����(gm);
						continue;
					}
					gm.sendPackets(new S_SystemMessage("�� ���� �ǽ� " + getName()
							+ " AC : " + ac.getAc()), true);
				}
			}
		}
	}

	public void ����üũ() {
		int checknum = 75;
		if (getAbility().getTotalStr() > checknum)
			statcheck(getAbility().getTotalStr(), 1);
		if (getAbility().getTotalCha() > checknum)
			statcheck(getAbility().getTotalCha(), 6);
		if (getAbility().getTotalDex() > checknum)
			statcheck(getAbility().getTotalDex(), 2);
		if (getAbility().getTotalCon() > checknum)
			statcheck(getAbility().getTotalCon(), 3);
		if (getAbility().getTotalWis() > checknum)
			statcheck(getAbility().getTotalWis(), 4);
		if (getAbility().getTotalInt() > checknum)
			statcheck(getAbility().getTotalInt(), 5);
	}

	private void statcheck(int i, int type) {
		String type_text = "";
		if (type == 1)
			type_text = "�� : ";
		else if (type == 2)
			type_text = "���� : ";
		else if (type == 3)
			type_text = "�� : ";
		else if (type == 4)
			type_text = "���� : ";
		else if (type == 5)
			type_text = "��Ʈ : ";
		else if (type == 6)
			type_text = "ī������ : ";
		if (Config.����ä�ø����() > 0) {
			for (L1PcInstance gm : Config.toArray����ä�ø����()) {
				if (gm.getNetConnection() == null) {
					Config.remove����(gm);
					continue;
				}
				gm.sendPackets(new S_SystemMessage("���� ���� �ǽ� " + getName()
						+ " " + type_text + i + " �ñ� ó����"), true);
				getNetConnection().close();
			}
		}
		type_text = null;
	}

	/*
	 * public void �Ƶ�üũ() { PrivateWarehouse wh = null; wh =
	 * WarehouseManager.getInstance().getPrivateWarehouse(getAccountName());
	 * L1ItemInstance item = wh.findItemId(40308); if(item != null){
	 * if(item.getCount() > GameServer.getInstance().�Ƶ�������){
	 * if(Config.����ä�ø����()>0){ for(L1PcInstance gm : Config.toArray����ä�ø����()){
	 * if(gm.getNetConnection()==null){ Config.remove����(gm); continue; }
	 * gm.sendPackets(new
	 * S_SystemMessage("â�� �Ƶ� ���� �ǽ� "+getName()+" �����Ƶ� "+item.getCount())); } }
	 * } } _�����Ƶ����� = getInventory().countItems(40308); if(_�����Ƶ����� >
	 * GameServer.getInstance().�Ƶ�������){ if(Config.����ä�ø����()>0){ for(L1PcInstance
	 * gm : Config.toArray����ä�ø����()){ if(gm.getNetConnection()==null){
	 * Config.remove����(gm); continue; } gm.sendPackets(new
	 * S_SystemMessage("�κ� �Ƶ� ���� �ǽ� "+getName()+" �����Ƶ� "+_�����Ƶ�����)); } } } }
	 */
	/*
	 * if(_�����Ƶ����� > _�����Ƶ�����){ int ���̰� = _�����Ƶ����� - _�����Ƶ�����;
	 * 
	 * if(���̰� ==_�����Ƶ����� ){ if(_�����Ƶ����� > 50000000){
	 * 
	 * } } _�����Ƶ����� = _�����Ƶ�����;
	 */
	// /����?
	private int _sealScrollTime;

	public void setSealScrollTime(int sealScrollTime) {
		_sealScrollTime = sealScrollTime;
	}

	public int getSealScrollTime() {
		return _sealScrollTime;
	}

	private int _sealScrollCount;

	public void setSealScrollCount(int sealScrollCount) {
		_sealScrollCount = sealScrollCount;
	}

	public int getSealScrollCount() {
		return _sealScrollCount;
	}

	private int _clanid;
	private String clanname;
	private int _clanRank;
	private byte _sex;
	private int _returnstat;
	private short _hpr = 0;
	private short _trueHpr = 0;
	private short _mpr = 0;
	private short _trueMpr = 0;

	private int _advenHp;
	private int _advenMp;
	private int _highLevel;
	
	private int rankLevel;

    public int getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(int i) {
        rankLevel = i;
    }

	private boolean _ghost = false;
	private boolean _isReserveGhost = false;
	private boolean _isShowTradeChat = true;
	private boolean _isCanWhisper = true;
	private boolean _isFishing = false;
	private boolean _isFishingReady = false;

	private boolean petRacing = false; // �극�̽�
	private int petRacingLAB = 1; // ���� LAB
	private int petRacingCheckPoint = 162; // ���籸��
	private boolean isHaunted = false;
	private boolean isDeathMatch = false;
	private boolean _isShowWorldChat = true;
	private boolean _gm;

	private boolean _Sgm;
	private boolean _monitor;
	private boolean _gmInvis;
	private boolean _isTeleport = false;
	private boolean _�ڴ�� = false;
	private boolean _isDrink = false;
	private boolean _isGres = false;
	private boolean _isPinkName = false;
	private boolean _banned;
	private boolean _gresValid;
	private boolean _tradeOk;
	private boolean _tradeReady;
	private boolean _mpRegenActiveByDoll;
	private boolean _mpDecreaseActiveByScales;
	private boolean _AHRegenActive;
	private boolean _SHRegenActive;
	private boolean _HalloweenRegenActive;
	private boolean _hpRegenActiveByDoll;
	private boolean _rpActive;
	
    public boolean RootMent = true;// ���� ��Ʈ]
	/** ������Ʈ�ý��� by��� **/
	public int LawfulAC = 0;
	public int LawfulMR = 0;
	public int LawfulSP = 0;
	public int LawfulAT = 0;
	/** ������Ʈ�ý��� by��� **/

	private int invisDelayCounter = 0;
	private Object _invisTimerMonitor = new Object();

	public int _ghostSaveLocX = 0;
	public int _ghostSaveLocY = 0;
	public short _ghostSaveMapId = 0;
	public int _ghostSaveHeading = 0;

	private ScheduledFuture<?> _ghostFuture;
	private ScheduledFuture<?> _hellFuture;
	private ScheduledFuture<?> _autoUpdateFuture;
	private ScheduledFuture<?> _expMonitorFuture;

	private Timestamp _lastPk;
	private Timestamp _deleteTime;

	private int _weightReduction = 0;
	private int _hasteItemEquipped = 0;
	private int _damageReductionByArmor = 0;
	 private int _regist_PVPweaponTotalDamage = 0; //PVP�����
	private int _PotionPlus = 0;

	private final L1ExcludingList _excludingList = new L1ExcludingList();
	private final L1ExcludingLetterList _excludingLetterList = new L1ExcludingLetterList();
	private final AcceleratorChecker _acceleratorChecker = new AcceleratorChecker(
			this);
	private ArrayList<Integer> skillList = new ArrayList<Integer>();


 
	private int _teleportY = 0;
	private int _teleportX = 0;
	private short _teleportMapId = 0;
	private int _teleportHeading = 0;
	private int _tempCharGfxAtDead;
	private int _fightId;
	private byte _chatCount = 0;
	private long _oldChatTimeInMillis = 0L;

	private int _elfAttr;
	private int _expRes;

	private int _onlineStatus;
	private int _homeTownId;
	private int _contribution;
	private int _food;
	private int _hellTime;
	private int _partnerId;
	private long _fishingTime = 0;
	private int _dessertId = 0;
	private int _callClanId;
	private int _callClanHeading;

	private int _currentWeapon;
	private final L1Karma _karma = new L1Karma();
	private final L1PcInventory _inventory;
	private final L1Inventory _tradewindow;

	private L1ItemInstance _weapon;
	private L1ItemInstance _secondweapon;
	private L1ItemInstance _armor;
	private L1Party _party;
	private L1ChatParty _chatParty;

	private int _cookingId = 0;
	private int _partyID;
	private int _partyType;
	private int _tradeID;
	private int _�ֽ�ID;
	private int _tempID;
	private int _ubscore;

	private Timestamp _clan_join_date;

	public void setClanJoinDate(Timestamp date) {
		_clan_join_date = date;
	}

	public Timestamp getClanJoinDate() {
		return _clan_join_date;
	}

	private L1Quest _quest;

	// �������ֱ�ð� �ڷ�Ǯ�� ���
	private long _quiztime = 0;

	public long getQuizTime() {
		return _quiztime;
	}

	public void setQuizTime(long l) {
		_quiztime = l;
	}

	// �������ֱ�ð�

	// �ɸ���ȯ
	private boolean _isChaTradeSlot = false;

	public boolean isChaTradeSlot() {
		return _isChaTradeSlot;
	}

	public void setChaTradeSlot(boolean is) {
		_isChaTradeSlot = is;
	}

	// private int _elixirStats;

	private String _sealingPW; // �� Ŭ����

	public String getSealingPW() {
		return _sealingPW;
	}

	public void setSealingPW(String s) {
		_sealingPW = s;
	}

	// ĳ���� ��ȯ
	/*
	 * public int getElixirStats() { return _elixirStats; }
	 * 
	 * public void setElixirStats(int i) { _elixirStats = i; } //ĳ���� ��ȯ
	 */

	// private HpRegeneration _hpRegen;
	// private MpRegeneration _mpRegen;
	private HpRegenerationByDoll _hpRegenByDoll;
	private MpRegenerationByDoll _mpRegenByDoll;
	private MpDecreaseByScales _mpDecreaseByScales;
	private AHRegeneration _AHRegen;
	private SHRegeneration _SHRegen;
	private HalloweenRegeneration _HalloweenRegen;
	private L1PartyRefresh _rp;
	private static Timer _regenTimer = new Timer(true);

	private boolean _isTradingInPrivateShop = false;
	private boolean _isPrivateShop = false;
	public boolean Gaho = false;
	private int _partnersPrivateShopItemCount = 0;

	private final ArrayList<L1BookMark> _bookmarks;
	private ArrayList<L1PrivateShopSellList> _sellList = new ArrayList<L1PrivateShopSellList>();
	private ArrayList<L1PrivateShopBuyList> _buyList = new ArrayList<L1PrivateShopBuyList>();

	private final Map<Integer, L1NpcInstance> _petlist = new HashMap<Integer, L1NpcInstance>();
	private final Map<Integer, L1DollInstance> _dolllist = new HashMap<Integer, L1DollInstance>();
	private final Map<Integer, L1FollowerInstance> _followerlist = new HashMap<Integer, L1FollowerInstance>();

	private byte[] _shopChat;
	private LineageClient _netConnection;
	private static Logger _log = Logger.getLogger(L1PcInstance.class.getName());
	private long lastSavedTime = System.currentTimeMillis();
	private long lastSavedTime_inventory = System.currentTimeMillis();

	private int adFeature = 1;

	public L1PcInstance() {
		super();
		_accessLevel = 0;
		_currentWeapon = 0;
		_inventory = new L1PcInventory(this);
		_tradewindow = new L1Inventory();
		_bookmarks = new ArrayList<L1BookMark>();
		_quest = new L1Quest(this);
		_equipSlot = new L1EquipmentSlot(this);
	}

	public int getadFeature() {
		return adFeature;
	}

	public void setadFeature(int count) {
		this.adFeature = count;
	}

	public long getlastSavedTime() {
		return lastSavedTime;
	}

	public long getlastSavedTime_inventory() {
		return lastSavedTime_inventory;
	}

	public void setlastSavedTime(long stime) {
		this.lastSavedTime = stime;
	}

	public void setlastSavedTime_inventory(long stime) {
		this.lastSavedTime_inventory = stime;
	}

	public void setSkillMastery(int skillid) {
		if (!skillList.contains(skillid)) {
			skillList.add(skillid);
		}
	}

	public void removeSkillMastery(int skillid) {
		if (skillList.contains((Object) skillid)) {
			skillList.remove((Object) skillid);
		}
	}

	public boolean isSkillMastery(int skillid) {
		return skillList.contains(skillid);
	}

	public long getSurvivalCry() {
		return _SurvivalCry;
	}

	public void setSurvivalCry(long SurvivalCry) {
		_SurvivalCry = SurvivalCry;
	}

	public void clearSkillMastery() {
		skillList.clear();
	}

	public short getHpr() {
		return _hpr;
	}

	public void addHpr(int i) {
		_trueHpr += i;
		_hpr = (short) Math.max(0, _trueHpr);
	}

	public short getMpr() {
		return _mpr;
	}

	public void addMpr(int i) {
		_trueMpr += i;
		_mpr = (short) Math.max(0, _trueMpr);
	}

	/*
	 * public void startHpRegeneration() { final int INTERVAL = 1000;
	 * 
	 * if (!_hpRegenActive) { _hpRegen = new HpRegeneration(this);
	 * _regenTimer.scheduleAtFixedRate(_hpRegen, INTERVAL, INTERVAL);
	 * _hpRegenActive = true; } }
	 */
	/*
	 * public void startRP() { final int INTERVAL = 25000; if (!_rpActive) { _rp
	 * = new L1PartyRefresh(this); _regenTimer.scheduleAtFixedRate(_rp,
	 * INTERVAL, INTERVAL); _rpActive = true; } }
	 */
	public void startHpRegenerationByDoll() {
		final int INTERVAL_BY_DOLL = 32000;
		boolean isExistHprDoll = false;

		for (L1DollInstance doll : getDollList()) {
			if (doll.isHpRegeneration()) {
				isExistHprDoll = true;
			}
		}
		if (!_hpRegenActiveByDoll && isExistHprDoll) {
			_hpRegenByDoll = new HpRegenerationByDoll(this);
			_regenTimer.scheduleAtFixedRate(_hpRegenByDoll, INTERVAL_BY_DOLL,
					INTERVAL_BY_DOLL);
			_hpRegenActiveByDoll = true;
		}
	}

	public void startAHRegeneration() {
		final int INTERVAL = 600000;
		if (!_AHRegenActive) {
			_AHRegen = new AHRegeneration(this);
			_regenTimer.scheduleAtFixedRate(_AHRegen, INTERVAL, INTERVAL);
			_AHRegenActive = true;
		}
	}

	public void startSHRegeneration() {
		final int INTERVAL = 1800000;
		if (!_SHRegenActive) {
			_SHRegen = new SHRegeneration(this);
			_regenTimer.scheduleAtFixedRate(_SHRegen, INTERVAL, INTERVAL);
			_SHRegenActive = true;
		}
	}

	public void startHalloweenRegeneration() {
		final int INTERVAL = 900000;
		if (!_HalloweenRegenActive) {
			_HalloweenRegen = new HalloweenRegeneration(this);
			_regenTimer
					.scheduleAtFixedRate(_HalloweenRegen, INTERVAL, INTERVAL);
			_HalloweenRegenActive = true;
		}
	}

	/*
	 * public void stopHpRegeneration() { if (_hpRegenActive) {
	 * _hpRegen.cancel(); _hpRegen = null; _hpRegenActive = false; } }
	 */

	public void stopHpRegenerationByDoll() {
		if (_hpRegenActiveByDoll) {
			_hpRegenByDoll.cancel();
			_hpRegenByDoll = null;
			_hpRegenActiveByDoll = false;
		}
	}

	public boolean ų��Ʈ = true;

	/*
	 * public void startMpRegeneration() { final int INTERVAL = 1000;
	 * 
	 * if (!_mpRegenActive) { _mpRegen = new MpRegeneration(this);
	 * _regenTimer.scheduleAtFixedRate(_mpRegen, INTERVAL, INTERVAL);
	 * _mpRegenActive = true; } }
	 */

	public void startMpRegenerationByDoll() {
		final int INTERVAL_BY_DOLL = 64000;
		boolean isExistMprDoll = false;

		for (L1DollInstance doll : getDollList()) {

			if (doll.isMpRegeneration()) {
				isExistMprDoll = true;
			}
		}
		if (!_mpRegenActiveByDoll && isExistMprDoll) {
			_mpRegenByDoll = new MpRegenerationByDoll(this);
			_regenTimer.scheduleAtFixedRate(_mpRegenByDoll, INTERVAL_BY_DOLL,
					INTERVAL_BY_DOLL);
			_mpRegenActiveByDoll = true;
		}
	}

	public void startMpDecreaseByScales() {
		final int INTERVAL_BY_SCALES = 4000;
		_mpDecreaseByScales = new MpDecreaseByScales(this);
		_regenTimer.scheduleAtFixedRate(_mpDecreaseByScales,
				INTERVAL_BY_SCALES, INTERVAL_BY_SCALES);
		_mpDecreaseActiveByScales = true;
	}

	/*
	 * public void stopMpRegeneration() { if (_mpRegenActive) {
	 * _mpRegen.cancel(); _mpRegen = null; _mpRegenActive = false; } }
	 */
	public void stopRP() {
		if (_rpActive) {
			_rp.cancel();
			_rp = null;
			_rpActive = false;
		}
	}

	public void stopMpRegenerationByDoll() {
		if (_mpRegenActiveByDoll) {
			_mpRegenByDoll.cancel();
			_mpRegenByDoll = null;
			_mpRegenActiveByDoll = false;
		}
	}

	public void stopMpDecreaseByScales() {
		if (_mpDecreaseActiveByScales) {
			_mpDecreaseByScales.cancel();
			_mpDecreaseByScales = null;
			_mpDecreaseActiveByScales = false;
		}
	}

	public void stopAHRegeneration() {
		if (_AHRegenActive) {
			_AHRegen.cancel();
			_AHRegen = null;
			_AHRegenActive = false;
		}
	}

	public void stopSHRegeneration() {
		if (_SHRegenActive) {
			_SHRegen.cancel();
			_SHRegen = null;
			_SHRegenActive = false;
		}
	}

	public void stopHalloweenRegeneration() {
		if (_HalloweenRegenActive) {
			_HalloweenRegen.cancel();
			_HalloweenRegen = null;
			_HalloweenRegenActive = false;
		}
	}

	public void startObjectAutoUpdate() {
		// final long INTERVAL_AUTO_UPDATE = 300;
		getNearObjects().removeAllKnownObjects();
		// _autoUpdateFuture =
		// GeneralThreadPool.getInstance().pcScheduleAtFixedRate(new
		// L1PcAutoUpdate(getId()), 0L, INTERVAL_AUTO_UPDATE);
		GeneralThreadPool.getInstance().pcSchedule(new L1PcAutoUpdate(this), 1);
	}

	public void stopEtcMonitor() {

		if (_autoUpdateFuture != null) {
			_autoUpdateFuture.cancel(true);
			_autoUpdateFuture = null;
		}
		if (_expMonitorFuture != null) {
			_expMonitorFuture.cancel(true);
			_expMonitorFuture = null;
		}

		if (_ghostFuture != null) {
			_ghostFuture.cancel(true);
			_ghostFuture = null;
		}

		if (_hellFuture != null) {
			_hellFuture.cancel(true);
			_hellFuture = null;
		}

	}

	public void stopEquipmentTimer() {
		List<L1ItemInstance> allItems = this.getInventory().getItems();
		for (L1ItemInstance item : allItems) {
			if (item.isEquipped() && item.getRemainingTime() > 0) {
				item.stopEquipmentTimer();
			}
		}
	}

	public void onChangeExp() {
		int level = ExpTable.getLevelByExp(getExp());
		int char_level = getLevel();
		int gap = level - char_level;
		if (gap == 0) {
			sendPackets(new S_OwnCharStatus(this));
			sendPackets(new S_PacketBox(S_PacketBox.char_ER, get_PlusEr()),
					true);
			int percent = ExpTable.getExpPercentage(char_level, getExp());
			if (char_level >= 60 && char_level <= 64) {
				if (percent >= 10)
					getSkillEffectTimerSet()
							.removeSkillEffect(L1SkillId.���������ʽ�);
			} else if (char_level >= 65) {
				if (percent >= 5) {
					getSkillEffectTimerSet()
							.removeSkillEffect(L1SkillId.���������ʽ�);
				}
			}
			// sendPackets(new S_Exp(this));
			return;
		}

		if (gap > 0) {
			levelUp(gap);
			if (getLevel() >= 60) {
				getSkillEffectTimerSet().setSkillEffect(L1SkillId.���������ʽ�,
						10800000);
				sendPackets(new S_PacketBox(10800, true, true), true);
			}
		} else if (gap < 0) {
			levelDown(gap);
			getSkillEffectTimerSet().removeSkillEffect(L1SkillId.���������ʽ�);
		}
	}

	@Override
	public void onPerceive(L1PcInstance pc) {
		if (isGhost()) {
			return;
		}
		if (pc.getMapId() == 2100) {
			return;
		}
		if (pc.getMapId() == 2699) {
			return;
		}

		/*
		 * if(isInvisble() &&
		 * !pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId
		 * .STATUS_FLOATING_EYE)){ return; }
		 */

		/*
		 * if(pc.getNearObjects().knownsObject(this)){ return; }
		 */

		pc.getNearObjects().addKnownObject(this);

		if ((isGm() && isGmInvis()) || (isSGm() && isGmInvis())) {

		} else {

			pc.sendPackets(new S_OtherCharPacks(this, pc), true);

			// ������ ���� �߰� Ȯ���ʿ�
			for (L1Character target : L1World.getInstance()
					.getVisiblePlayer(pc)) {
				if (target instanceof L1PcInstance) {
					L1PcInstance _target = (L1PcInstance) target;
					if (_target.isPinkName()) {
						int time = _target.getSkillEffectTimerSet()
								.getSkillEffectTimeSec(
										L1SkillId.STATUS_PINK_NAME);
						if (time <= 0) {
							_target.setPinkName(false);
							pc.sendPackets(new S_PinkName(_target.getId(), 0),
									true);
						} else
							pc.sendPackets(
									new S_PinkName(_target.getId(), time), true);
					}
				} else if (target instanceof L1NpcInstance) {
					L1NpcInstance _target = (L1NpcInstance) target;
					if (_target.isPinkName()) {
						int time = _target.getSkillEffectTimerSet()
								.getSkillEffectTimeSec(
										L1SkillId.STATUS_PINK_NAME);
						if (time <= 0) {
							_target.setPinkName(false);
							pc.sendPackets(new S_PinkName(_target.getId(), 0),
									true);
						} else
							pc.sendPackets(
									new S_PinkName(_target.getId(), time), true);
					}
				}
			}

			/*
			 * if (isInParty() && getParty().isMember(pc)) { if(!isInvisble())
			 * pc.sendPackets(new S_HPMeter(this), true); }
			 */

			if (pc.isInParty() && pc.getParty().isMember(this)) {
				// if(!pc.isInvisble()){
				// System.out.println(getName() +
				// " : ���� "+pc.getName()+"�� �ǹ� ǥ��");
				if (pc.ǥ�� != 0) {
					sendPackets(new S_NewUI(0x53, pc));
				}
				sendPackets(new S_HPMeter(pc));
				// }
				// if(!isInvisble()){
				if (ǥ�� != 0) {
					sendPackets(new S_NewUI(0x53, this));
				}
				// System.out.println(pc.getName() +
				// " : ���� "+getName()+"�� �ǹ� ǥ��");
				pc.sendPackets(new S_HPMeter(this));
				// }
			}
			if (isFishing()) {
				if (fishX != 0 && fishY != 0)
					pc.sendPackets(new S_Fishing(getId(),
							ActionCodes.ACTION_Fishing, fishX, fishY), true);
				else
					pc.sendPackets(new S_Fishing(getId(),
							ActionCodes.ACTION_Fishing, getX(), getY()), true);
			}

			if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.TRUE_TARGET)) {
				if (tt_clanid == pc.getClanid()
						|| tt_partyid == pc.getPartyID()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.�̹�������, getId(),
							12299, true));
				}
			}
		}

		// if (isPrivateShop()) {
		// pc.sendPackets(new S_DoActionShop(getId(),ActionCodes.ACTION_Shop,
		// getShopChat()));
		// }
	}

	boolean updateCk = false;
	/*
	 * public void updateObject22() { if(updateCk) return; updateCk = true; try{
	 * S_RemoveObject sr2 = new S_RemoveObject(this); for (L1Object known :
	 * getNearObjects().getKnownObjects()) { if (known == null) { continue; } if
	 * (Config.PC_RECOGNIZE_RANGE == -1) { if
	 * (!getLocation().isInScreen(known.getLocation())) {
	 * getNearObjects().removeKnownObject(known); sendPackets(new
	 * S_RemoveObject(known), true); if(known instanceof L1Character){
	 * if(((L1Character)known).getNearObjects().knownsObject(this)){
	 * ((L1Character)known).getNearObjects().removeKnownObject(this); if(known
	 * instanceof L1PcInstance){ ((L1PcInstance)known).sendPackets(sr2); } } } }
	 * }else { if (getLocation().getTileLineDistance(known.getLocation()) >
	 * Config.PC_RECOGNIZE_RANGE) { getNearObjects().removeKnownObject(known);
	 * sendPackets(new S_RemoveObject(known), true); if(known instanceof
	 * L1Character){
	 * if(((L1Character)known).getNearObjects().knownsObject(this)){
	 * ((L1Character)known).getNearObjects().removeKnownObject(this); if(known
	 * instanceof L1PcInstance){ ((L1PcInstance)known).sendPackets(sr2); } } } }
	 * } } for (L1Object visible : L1World.getInstance().getVisibleObjects(this,
	 * Config.PC_RECOGNIZE_RANGE)) { if
	 * (!getNearObjects().knownsObject(visible)) { visible.onPerceive(this); }
	 * else { if (visible instanceof L1NpcInstance) { L1NpcInstance npc =
	 * (L1NpcInstance) visible; if (getLocation().isInScreen(npc.getLocation())
	 * && npc.getHiddenStatus() != 0) { npc.approachPlayer(this); } } }
	 * 
	 * if(visible instanceof L1PcInstance){ if
	 * (!((L1PcInstance)visible).getNearObjects().knownsObject(this))
	 * this.onPerceive(((L1PcInstance)visible)); }
	 * 
	 * if(visible instanceof L1PcInstance){ L1PcInstance player =
	 * (L1PcInstance)visible; if (player.isPrivateShop()) { sendPackets(new
	 * S_DoActionShop(player.getId(),ActionCodes.ACTION_Shop, getShopChat()));
	 * System.out.println("�ȳ� ���� �����̾� ! ���̸��� "+player.getName()); } }else
	 * if(visible instanceof L1NpcShopInstance){ L1NpcShopInstance player =
	 * (L1NpcShopInstance)visible; sendPackets(new
	 * S_DoActionShop(player.getId(),ActionCodes.ACTION_Shop, getShopChat()));
	 * System.out.println("�ȳ� ���� �����̾� ! ���̸��� "+player.getName()); }
	 * 
	 * if(visible instanceof L1PcInstance){ L1PcInstance pc =
	 * (L1PcInstance)visible;
	 * if(pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.TRUE_TARGET)){
	 * if(pc.tt_clanid == getClanid()||pc.tt_partyid == getPartyID()){
	 * sendPackets(new S_PacketBox(S_PacketBox.�̹�������,pc.getId(),12299,true)); }
	 * } }else if(visible instanceof L1NpcInstance){ L1NpcInstance npc =
	 * (L1NpcInstance)visible;
	 * if(npc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.TRUE_TARGET)){
	 * if(npc.tt_clanid == getClanid()||npc.tt_partyid == getPartyID()){
	 * sendPackets(new S_PacketBox(S_PacketBox.�̹�������,npc.getId(),12299,true)); }
	 * } } if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.GMSTATUS_HPBAR)
	 * && L1HpBar.isHpBarTarget(visible)) { sendPackets(new
	 * S_HPMeter((L1Character) visible), true); } } }catch(Exception e){
	 * e.printStackTrace(); }finally{ updateCk = false; }
	 * 
	 * }
	 */
	private int pethpbarc = 0;

	public void updateObject() {
		try {
			for (L1Object known : getNearObjects().getKnownObjects()) {
				if (known == null) {
					continue;
				}
				if (Config.PC_RECOGNIZE_RANGE == -1) {
					if (!getLocation().isInScreen(known.getLocation())) {
						getNearObjects().removeKnownObject(known);
						sendPackets(new S_RemoveObject(known));
					}
				} else {
					if (getLocation().getTileLineDistance(known.getLocation()) > Config.PC_RECOGNIZE_RANGE) {
						getNearObjects().removeKnownObject(known);
						sendPackets(new S_RemoveObject(known));
						// if(!(known instanceof L1PcInstance) && known
						// instanceof L1Character){
						// ((L1Character)
						// known).getNearObjects().removeKnownObject(this);
						// }
					}
				}
			}

			for (L1Object visible : L1World.getInstance().getVisibleObjects(
					this, Config.PC_RECOGNIZE_RANGE)) {
				try {
					/*
					 * if(visible instanceof L1PcInstance){ L1PcInstance pc =
					 * (L1PcInstance)visible; if(pc.getMapId() == 2699){
					 * continue; } }
					 */
					if (!getNearObjects().knownsObject(visible)) {
						visible.onPerceive(this);
						// if(!(visible instanceof L1PcInstance) && visible
						// instanceof L1Character){
						// ((L1Character)
						// visible).getNearObjects().addKnownObject(this);
						// }
					} else {
						if (visible instanceof L1NpcInstance) {
							L1NpcInstance npc = (L1NpcInstance) visible;
							if (npc.getHiddenStatus() != 0
									&& getLocation().isInScreen(
											npc.getLocation())) {
								npc.approachPlayer(this);
							}
						}
					}
					if (visible instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) visible;

						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.TRUE_TARGET)) {
							if (pc.tt_clanid == getClanid()
									|| pc.tt_partyid == getPartyID()) {
								sendPackets(new S_PacketBox(S_PacketBox.�̹�������,
										pc.getId(), 12299, true));
							}
						}
					} else if (visible instanceof L1NpcInstance) {
						L1NpcInstance npc = (L1NpcInstance) visible;
						if (npc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.TRUE_TARGET)) {
							if (npc.tt_clanid == getClanid()
									|| npc.tt_partyid == getPartyID()) {
								sendPackets(new S_PacketBox(S_PacketBox.�̹�������,
										npc.getId(), 12299, true));
							}
						}
					}

					if (getPetListSize() > 0) {
						if (pethpbarc >= 4) {
							pethpbarc = 0;
							L1PetInstance pets = null;
							L1SummonInstance summon = null;
							for (Object pet : getPetList()) {
								if (pet instanceof L1PetInstance) {
									pets = (L1PetInstance) pet;
									sendPackets(new S_HPMeter(pets));
								} else if (pet instanceof L1SummonInstance) {
									summon = (L1SummonInstance) pet;
									sendPackets(new S_HPMeter(summon));
								}
							}
						} else {
							pethpbarc++;
						}
					}
					if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.GMSTATUS_HPBAR)
							&& L1HpBar.isHpBarTarget(visible)) {
						sendPackets(new S_HPMeter((L1Character) visible));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendVisualEffect() {
		int poisonId = 0;
		if (getPoison() != null) {
			poisonId = getPoison().getEffectId();
		}
		if (getParalysis() != null) {
			poisonId = getParalysis().getEffectId();
		}
		if (poisonId != 0) {
			sendPackets(new S_Poison(getId(), poisonId), true);
			Broadcaster.broadcastPacket(this, new S_Poison(getId(), poisonId),
					true);
		}
	}

	public void sendVisualEffectAtLogin() {
		/*
		 * for (int i = 1 ; i < 9 ; i++ ) { HashMap<Integer, L1Clan> c =
		 * ClanTable.getInstance().getClanCastles(); L1Clan clan = c.get(i); if
		 * (clan != null) { sendPackets(new S_CastleMaster(i,
		 * clan.getLeaderId())); } }
		 */
		sendVisualEffect();
	}

	public void sendCastleMaster() {
		if (getClanid() != 0) {
			L1Clan clan = L1World.getInstance().getClan(getClanname());
			if (clan != null) {
				if (isCrown() && getId() == clan.getLeaderId()
						&& clan.getCastleId() != 0) {
					sendPackets(
							new S_CastleMaster(clan.getCastleId(), getId()),
							true);
				}
			}
		}
	}

	public void sendVisualEffectAtTeleport() {
		if (isDrink()) {
			sendPackets(new S_Liquor(getId()), true);
		}
		sendVisualEffect();
	}

	@Override
	public void setCurrentHp(int i) {
		if (getCurrentHp() == i)
			return;
		if (isGm() || isSGm())
			i = getMaxHp();
		super.setCurrentHp(i);
		sendPackets(new S_HPUpdate(getCurrentHp(), getMaxHp()), true);
		if (isInParty())
			getParty().updateMiniHP(this);
	    /** ��Ʋ�� **/
        if (getMapId() == 5153 && get_DuelLine() != 0) {
            for (L1PcInstance member : BattleZone.getInstance().toArray��Ʋ������()) {
                if (member != null) {
                    if (get_DuelLine() == member.get_DuelLine()) {
                        member.sendPackets(new S_HPMeter(this));
                    }
                }
            }
        }

	}

	@Override
	  public void setCurrentMp(int i) {
        if (getCurrentMp() == i)
            return;
        if (isGm())
            i = getMaxMp();
        super.setCurrentMp(i);
        sendPackets(new S_MPUpdate(getCurrentMp(), getMaxMp()));
        if (isInParty()) {
            getParty().updateMiniHP(this);
        }
        /** ��Ʋ�� **/
        if (getMapId() == 5153 && get_DuelLine() != 0) {
            for (L1PcInstance member : BattleZone.getInstance().toArray��Ʋ������()) {
                if (member != null) {
                    if (get_DuelLine() == member.get_DuelLine()) {
                        member.sendPackets(new S_HPMeter(this));
                    }
                }
            }
        }
    }

	@Override
	public L1PcInventory getInventory() {
		return _inventory;
	}

	public L1Inventory getTradeWindowInventory() {
		return _tradewindow;
	}

	public boolean isGmInvis() {
		return _gmInvis;
	}
	
	public int getDamageReductionByArmor() {
        return _damageReductionByArmor;
    }

    public void addDamageReductionByArmor(int i) {
        _damageReductionByArmor += i;
    }

    public int get_regist_PVPweaponTotalDamage() {
        return _regist_PVPweaponTotalDamage;
    }

	public void setGmInvis(boolean flag) {
		_gmInvis = flag;
	}

	public int getCurrentWeapon() {
		return _currentWeapon;
	}

	public void setCurrentWeapon(int i) {
		_currentWeapon = i;
	}

	public int getType() {
		return _type;
	}

	public void setType(int i) {
		_type = (short) i;
	}

	public short getAccessLevel() {
		return _accessLevel;
	}

	public void setAccessLevel(short i) {
		_accessLevel = i;
	}

	public short getClassId() {
		return _classId;
	}

	public void setClassId(int i) {
		_classId = (short) i;
		_classFeature = L1ClassFeature.newClassFeature(i);
	}

	public L1ClassFeature getClassFeature() {
		return _classFeature;
	}

	@Override
	public synchronized int getExp() {
		return _exp;
	}

	@Override
	public synchronized void setExp(int i) {
		_exp = i;
	}

	public synchronized int getReturnStat() {
		return _returnstat;
	}

	public synchronized void setReturnStat(int i) {

		_returnstat = i;
	}

	private L1PcInstance getStat() {
		return null;
	}

	public void reduceCurrentHp(double d, L1Character l1character) {
		getStat().reduceCurrentHp(d, l1character);
	}

	private void notifyPlayersLogout(List<L1PcInstance> playersArray) {
		S_RemoveObject ro = new S_RemoveObject(this);
		for (L1PcInstance player : playersArray) {
			if (player.getNearObjects().knownsObject(this)) {
				player.getNearObjects().removeKnownObject(this);
				player.sendPackets(ro);
			}
		}
	}

	private PapuBlessing _PapuRegen;
	private boolean _PapuBlessingActive;

	public void startPapuBlessing() {
		final int RegenTime = 120000;
		if (!_PapuBlessingActive) {
			_PapuRegen = new PapuBlessing(this);
			_regenTimer.scheduleAtFixedRate(_PapuRegen, RegenTime, RegenTime);
			_PapuBlessingActive = true;
		}
	}

	public void stopPapuBlessing() {
		if (_PapuBlessingActive) {
			_PapuRegen.cancel();
			_PapuRegen = null;
			_PapuBlessingActive = false;
		}
	}

	private HalloweenArmorBlessing _HalloweenArmor;
	private boolean _HalloweenArmorBlessingActive;

	public void startHalloweenArmorBlessing() {
		final int RegenTime = 120000;
		if (!_HalloweenArmorBlessingActive) {
			_HalloweenArmor = new HalloweenArmorBlessing(this);
			_regenTimer.scheduleAtFixedRate(_HalloweenArmor, RegenTime,
					RegenTime);
			_HalloweenArmorBlessingActive = true;
		}
	}

	public void stopHalloweenArmorBlessing() {
		if (_HalloweenArmorBlessingActive) {
			_HalloweenArmor.cancel();
			_HalloweenArmor = null;
			_HalloweenArmorBlessingActive = false;
		}
	}

	private LindBlessing _LindRegen;
	private boolean _LindBlessingActive;

	public void startLindBlessing() {
		final int RegenTime = 40000;
		if (!_LindBlessingActive) {
			_LindRegen = new LindBlessing(this);
			_regenTimer.scheduleAtFixedRate(_LindRegen, RegenTime, RegenTime);
			_LindBlessingActive = true;
		}
	}

	public void stopLindBlessing() {
		if (_LindBlessingActive) {
			_LindRegen.cancel();
			_LindRegen = null;
			_LindBlessingActive = false;
		}
	}

	public void ���������۸��Ի���(int objid, int itemid, int type2) {// �����۾��̵� �Ǹ� ����
																// ������ ����
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("DELETE FROM character_shop WHERE obj_id=? AND item_id=? AND type=?");
			pstm.setInt(1, objid);
			pstm.setInt(2, itemid);
			pstm.setInt(3, type2);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void ���������ۻ���(int objid, int itemid, int type2) {// �����۾��̵� �Ǹ� ���� ������
															// ����
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("DELETE FROM character_shop WHERE obj_id=? AND item_objid=? AND type=?");
			pstm.setInt(1, objid);
			pstm.setInt(2, itemid);
			pstm.setInt(3, type2);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void ���������۸��Ծ�����Ʈ(int objid, int itemid, int type2, int count1) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE character_shop SET count=? WHERE obj_id=? AND item_id=? AND type=?");
			pstm.setInt(1, count1);
			pstm.setInt(2, objid);
			pstm.setInt(3, itemid);
			pstm.setInt(4, type2);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void ���������۾�����Ʈ(int objid, int itemid, int type2, int count1) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE character_shop SET count=? WHERE obj_id=? AND item_objid=? AND type=?");
			pstm.setInt(1, count1);
			pstm.setInt(2, objid);
			pstm.setInt(3, itemid);
			pstm.setInt(4, type2);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void ���������ۻ���(int objid) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("DELETE FROM character_shop WHERE obj_id=?");
			pstm.setInt(1, objid);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void SaveShop(L1PcInstance pc, L1ItemInstance item, int price,
			int sellcount, int type) {
		Connection con = null;
		int bless = item.getBless();
		int attr = item.getAttrEnchantLevel();
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("INSERT INTO character_shop SET obj_id=?, char_name=?, item_objid=?, item_id=?, Item_name=?, count=?, enchant=?, price=?, type=?, locx=?, locy=?, locm=?, iden=?, attr=?");
			pstm.setInt(1, pc.getId());
			pstm.setString(2, pc.getName());
			pstm.setInt(3, item.getId());
			pstm.setInt(4, item.getItemId());
			pstm.setString(5, item.getItem().getName());
			pstm.setInt(6, sellcount);
			pstm.setInt(7, item.getEnchantLevel());
			pstm.setInt(8, price);
			pstm.setInt(9, type);// �Ǹ� 0 ���� 1
			pstm.setInt(10, pc.getX());
			pstm.setInt(11, pc.getY());
			pstm.setInt(12, pc.getMapId());
			/*
			 * 0 = ��Ȯ 1 = Ȯ�κ��� 2 = �� 3 = ����
			 */
			if (!item.isIdentified()) {
				pstm.setInt(13, 0);
			} else {
				switch (bless) {
				case 0:
					pstm.setInt(13, 2);
					break;// ��
				case 1:
					pstm.setInt(13, 1);
					break;// ����
				case 2:
					pstm.setInt(13, 3);
					break;// ����
				}
			}
			pstm.setInt(14, attr);
			/*
			 * if (item.isIdentified()) { pstm.setInt(13, 1); }else{
			 * pstm.setInt(13, 0); }
			 */

			pstm.executeUpdate();
		} catch (SQLException e) {
		} catch (Exception e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void logout() {
		try {
			if (GMCommands.�����̸�üũ) {
				if (C_SelectCharacter.nameINOUTList.contains(getName()))
					C_SelectCharacter.nameINOUTList.remove(getName());
			}
			L1World world = L1World.getInstance();
			notifyPlayersLogout(getNearObjects().getKnownPlayers());
			world.removeVisibleObject(this);
			world.removeObject(this);
			notifyPlayersLogout(world.getRecognizePlayer(this));
			stopEquipmentTimer();
			_inventory.clearItems();
			WarehouseManager w = WarehouseManager.getInstance();
			w.delPrivateWarehouse(this.getAccountName());
			w.delElfWarehouse(this.getAccountName());
			w.delPackageWarehouse(this.getAccountName());

			getNearObjects().removeAllKnownObjects();
			// stopHpRegeneration();
			// stopMpRegeneration();

			if (getClanid() != 0) {
				getClan().removeOnlineClanMember(getName());
			}

			stopHalloweenRegeneration();
			stopPapuBlessing();
			stopLindBlessing();
			stopHalloweenArmorBlessing();
			stopAHRegeneration();
			stopHpRegenerationByDoll();
			stopMpRegenerationByDoll();
			stopSHRegeneration();
			stopMpDecreaseByScales();

			stopEtcMonitor();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// setDead(true);

		/*
		 * _classFeature = null; _equipSlot = null; _accountName = null;
		 * antatime = null; giranday = null; ivoryday = null; ravaday = null;
		 * lindtime = null; papootime = null; DEtime = null; DEtime2 = null;
		 * _invisTimerMonitor = null; _lastPk = null; _deleteTime = null;
		 * skillList = null; _weapon = null; _armor = null; _party = null;
		 * _chatParty = null; _quest = null; _sealingPW = null; _rp = null;
		 * _sellList = null; _buyList = null; if(_petlist.size() > 0)
		 * _petlist.clear(); if(_dolllist.size() > 0) _dolllist.clear();
		 * if(_followerlist.size() > 0)_followerlist.clear(); _shopChat = null;
		 * _netConnection = null;
		 */
	}

	public LineageClient getNetConnection() {
		return _netConnection;
	}

	public void setNetConnection(LineageClient clientthread) {
		_netConnection = clientthread;
	}

	public boolean isInParty() {
		return getParty() != null;
	}

	public L1Party getParty() {
		return _party;
	}

	public void setParty(L1Party p) {
		_party = p;
	}

	public boolean isInChatParty() {
		return getChatParty() != null;
	}

	public L1ChatParty getChatParty() {
		return _chatParty;
	}

	public void setChatParty(L1ChatParty cp) {
		_chatParty = cp;
	}

	public int getPartyID() {
		return _partyID;
	}

	public void setPartyID(int partyID) {
		_partyID = partyID;
	}

	public int getPartyType() {
		return _partyType;
	}

	public void setPartyType(int partyType) {
		_partyType = partyType;
	}

	public int getTradeID() {
		return _tradeID;
	}

	public void setTradeID(int tradeID) {
		_tradeID = tradeID;
	}

	public int get�ֽþ��̵�() {
		return _�ֽ�ID;
	}

	public void set�ֽþ��̵�(int tradeID) {
		_�ֽ�ID = tradeID;
	}

	public void setTradeReady(boolean tradeOk) {
		_tradeReady = tradeOk;
	}

	public boolean getTradeReady() {
		return _tradeReady;
	}

	public void setTradeOk(boolean tradeOk) {
		_tradeOk = tradeOk;
	}

	public boolean getTradeOk() {
		return _tradeOk;
	}

	public int getTempID() {
		return _tempID;
	}

	public void setTempID(int tempID) {
		_tempID = tempID;
	}

	public boolean isTeleport() {
		return _isTeleport;
	}

	public void setTeleport(boolean flag) {
		_isTeleport = flag;
	}

	public boolean �ڴ��() {
		return _�ڴ��;
	}

	public void �ڴ��(boolean flag) {
		_�ڴ�� = flag;
	}

	public boolean isDrink() {
		return _isDrink;
	}

	public void setDrink(boolean flag) {
		_isDrink = flag;
	}

	public boolean isGres() {
		return _isGres;
	}

	public void setGres(boolean flag) {
		_isGres = flag;
	}

	public boolean isPinkName() {
		return _isPinkName;
	}

	public void setPinkName(boolean flag) {
		_isPinkName = flag;
	}

	public ArrayList<L1PrivateShopSellList> getSellList() {
		return _sellList;
	}

	public ArrayList<L1PrivateShopBuyList> getBuyList() {
		return _buyList;
	}

	public void setShopChat(byte[] chat) {
		_shopChat = chat;
	}

	public byte[] getShopChat() {
		return _shopChat;
	}

	public boolean isPrivateShop() {
		return _isPrivateShop;
	}

	public void setPrivateShop(boolean flag) {
		_isPrivateShop = flag;
	}

	public boolean isTradingInPrivateShop() {
		return _isTradingInPrivateShop;
	}

	public void setTradingInPrivateShop(boolean flag) {
		_isTradingInPrivateShop = flag;
	}

	public int getPartnersPrivateShopItemCount() {
		return _partnersPrivateShopItemCount;
	}

	public void setPartnersPrivateShopItemCount(int i) {
		_partnersPrivateShopItemCount = i;
	}

	public void sendPackets(ServerBasePacket serverbasepacket, boolean clear) {
		try {
			if ((getMapId() == 2699 || getMapId() == 2100)
					&& serverbasepacket.getType().equalsIgnoreCase(
							"[S] S_OtherCharPacks")) {
			} else
				sendPackets(serverbasepacket);
			if (clear) {
				serverbasepacket.clear();
				serverbasepacket = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendPackets(ServerBasePacket serverbasepacket) {
		if (this instanceof L1RobotInstance) {
			if (serverbasepacket.getType().equalsIgnoreCase("[S] S_Paralysis")) {
				if (serverbasepacket.getBytes()[1] == 2
						|| serverbasepacket.getBytes()[1] == 4
						|| serverbasepacket.getBytes()[1] == 10
						|| serverbasepacket.getBytes()[1] == 12
						|| serverbasepacket.getBytes()[1] == 22
						|| serverbasepacket.getBytes()[1] == 24) {
					this.setParalyzed(true);
				}
				if (serverbasepacket.getBytes()[1] == 3
						|| serverbasepacket.getBytes()[1] == 5
						|| serverbasepacket.getBytes()[1] == 11
						|| serverbasepacket.getBytes()[1] == 13
						|| serverbasepacket.getBytes()[1] == 23
						|| serverbasepacket.getBytes()[1] == 25) {
					this.setParalyzed(false);
				}
			}
			if (serverbasepacket.getType().equalsIgnoreCase("[S] S_Paralysis")) {
				if (serverbasepacket.getBytes()[1] == 2
						|| serverbasepacket.getBytes()[1] == 4
						|| serverbasepacket.getBytes()[1] == 10
						|| serverbasepacket.getBytes()[1] == 12
						|| serverbasepacket.getBytes()[1] == 22
						|| serverbasepacket.getBytes()[1] == 24) {
					this.setParalyzed(true);
				}
				if (serverbasepacket.getBytes()[1] == 3
						|| serverbasepacket.getBytes()[1] == 5
						|| serverbasepacket.getBytes()[1] == 11
						|| serverbasepacket.getBytes()[1] == 13
						|| serverbasepacket.getBytes()[1] == 23
						|| serverbasepacket.getBytes()[1] == 25) {
					this.setParalyzed(false);
				}
			}
			return;
		}

		if (�����) {
			if (serverbasepacket.getType().equalsIgnoreCase("[S] S_SabuTell")) {
				L1Teleport.�н���(this, dx, dy, dm, true);
			}
		}

		if (getNetConnection() == null) {
			return;
		}

		if (getNetConnection().getActiveChar() == null) {
			return;
		}

		if (getNetConnection().getActiveChar().getId() != getId()) {
			return;
		}

		if (�����) {
			return;
		}

		if (zombie) {
			return;
		}

		try {
			if (Config.���ο���Ŷ����) {
				if (_out == null)
					return;
				_out.sendPacket(serverbasepacket);
			} else {
				getNetConnection().sendPacket(serverbasepacket);
			}
			// getNetConnection().sendPacket(serverbasepacket);
		} catch (Exception e) {
		}
	}

    
	@Override
	public void onAction(L1PcInstance attacker, int adddmg) {
		Random random = new Random();
		try {
			if (!�����) {
				if (attacker == null) {
					return;
				}
				if (isTeleport()) {
					return;
				}
				if (CharPosUtil.getZoneType(this) == 1
						|| CharPosUtil.getZoneType(attacker) == 1) {
					L1Attack attack_mortion = new L1Attack(attacker, this);
					attack_mortion.action();
					attack_mortion = null;
					return;
				}

				if (checkNonPvP(this, attacker) == true) {
					L1Attack attack_mortion = new L1Attack(attacker, this);
					attack_mortion.action();
					attack_mortion = null;
					return;
				}
			}
			if (getCurrentHp() > 0 && !isDead()) {
				attacker.delInvis();
				boolean isCounterBarrier = false;
				boolean isTaitanrock = false;

				boolean isTaitanMagic = false;

				boolean isTaitanBllit = false;
				boolean isMortalBody = false;
				boolean isLindArmor = false;
				L1Attack attack = new L1Attack(attacker, this);
				if (attack.calcHit()) {
					if (attacker.getWeapon() != null) {
						int rock=0;
						
						if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.Ÿ��ź����¡)){ //Ÿ��ź����¡
							rock = 5;
							if(getLevel() > 80)
								rock += getLevel()-80;
							if(rock > 10)
								rock = 10;	

						}
						if (isTaitanM
								&& attacker.getWeapon().getItem().getType() == 17) {
							int hpRatio = 100;
							if (0 < getMaxHp()) {
								hpRatio = 100 * getCurrentHp() / getMaxHp();
							}
							if (hpRatio < 41 + rock) {
								int chan = random.nextInt(100) + 1;
								boolean isProbability = false;
								if (getInventory().checkItem(41246, 10)) {
									if (25 > chan) {
										isProbability = true;
									}
								}
								if (getSkillEffectTimerSet().hasSkillEffect(
										L1SkillId.SHOCK_STUN)
										|| getSkillEffectTimerSet()
												.hasSkillEffect(
														L1SkillId.EARTH_BIND)) {
									isProbability = false;
								}
								if (isProbability) {
									getInventory().consumeItem(41246, 10);
									isTaitanMagic = true;
								}
							}

						}
						// }else{
						if (isTaitanR) {
							if (attacker.getWeapon().getItem().getType() != 4
									&& attacker.getWeapon().getItem().getType() != 10
									&& attacker.getWeapon().getItem().getType() != 17
									&& attacker.getWeapon().getItem().getType() != 13) {
								int hpRatio = 100;
								if (0 < getMaxHp()) {
									hpRatio = 100 * getCurrentHp() / getMaxHp();
								}
								if (hpRatio < 41 + rock) {
									int chan = random.nextInt(100) + 1;
									boolean isProbability = false;
									if (getInventory().checkItem(41246, 10)) {
										if (25 > chan) {
											isProbability = true;

										}
									}
									if (getSkillEffectTimerSet()
											.hasSkillEffect(
													L1SkillId.SHOCK_STUN)
											|| getSkillEffectTimerSet()
													.hasSkillEffect(
															L1SkillId.EARTH_BIND)) {
										isProbability = false;
									}
									boolean isShortDistance = attack
											.isShortDistance();
									if (isProbability && isShortDistance) {
										isTaitanrock = true;
										getInventory().consumeItem(41246, 10);
									}
								}
							}
						}
						if (isTaitanB) {
							if (attacker.getWeapon() != null) {
								if (attacker.getWeapon().getItem().getType() == 4
										|| attacker.getWeapon().getItem()
												.getType() == 10
										|| attacker.getWeapon().getItem()
												.getType() == 13) {
									int hpRatio = 100;
									if (0 < getMaxHp()) {
										hpRatio = 100 * getCurrentHp()
												/ getMaxHp();
									}
									if (hpRatio < 41 + rock) {
										int chan = random.nextInt(100) + 1;
										boolean isProbability = false;
										if (getInventory().checkItem(41246, 10)) {
											if (25 > chan) {
												isProbability = true;
												getInventory().consumeItem(
														41246, 10);
											}
										}
										if (getSkillEffectTimerSet()
												.hasSkillEffect(
														L1SkillId.SHOCK_STUN)
												|| getSkillEffectTimerSet()
														.hasSkillEffect(
																L1SkillId.EARTH_BIND)) {
											isProbability = false;
										}
										if (isProbability) {
											isTaitanBllit = true;
										}
									}
								}
							}
						}
					 }
				    
					if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.COUNTER_BARRIER)
							&& attacker.getWeapon() != null
							&& attacker.getWeapon().getItem().getType() != 17) {
						if (getWeapon() != null
								&& getWeapon().getItem().getType1() == 50) {
							int chan = random.nextInt(100) + 1;
							boolean isProbability = false;
							if (18 > chan) {
								isProbability = true;
							}
							boolean isShortDistance = attack.isShortDistance();
							if (isProbability && isShortDistance) {
								isCounterBarrier = true;
							}
						}
					} else if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.MORTAL_BODY) /*
													 * && attacker.getWeapon()
													 * != null &&
													 * attacker.getWeapon
													 * ().getItem().getType() !=
													 * 17
													 */) {
						int chan = random.nextInt(100) + 1;
						boolean isProbability = false;
						if (18 > chan) {
							isProbability = true;
						}
						// boolean isShortDistance = attack.isShortDistance();
						if (isProbability /* && isShortDistance */) {
							isMortalBody = true;
						}
					} else {
						/*
						 * for(L1ItemInstance item : getInventory().getItems()){
						 * if(item.isEquipped() && item.getItemId() >= 420108 &&
						 * item.getItemId() <= 420111){ if(attacker.getWeapon()
						 * != null){
						 * if(attacker.getWeapon().getItem().getType1() == 20 ||
						 * attacker.getWeapon().getItem().getType1() == 62){ int
						 * chan = random.nextInt(100);
						 * if(item.getEnchantLevel()*2 > chan){ isLindArmor =
						 * true; } } } break; } } /* int chan =
						 * random.nextInt(100)+1; if(15 > chan){
						 * if(attacker.getWeapon() != null){
						 * if(attacker.getWeapon().getItem().getType1() == 20 ||
						 * attacker.getWeapon().getItem().getType1() == 62){
						 * isLindArmor = true; } } }
						 */
					}
					if (!isTaitanBllit && !isTaitanrock && !isCounterBarrier /*
																			 * &&
																			 * !
																			 * isMortalBody
																			 */
							&& !isLindArmor) {
						// attacker.setPetTarget(this);
						attack.calcDamage(adddmg);
						attack.calcStaffOfMana();
						attack.calcDrainOfHp();
						attack.addPcPoisonAttack(attacker, this);
					}
				}
				if (isTaitanMagic) {
					attack.actionTaitan(1);
					attack.commitTaitan(0);
					// attack.actionTaitan(1);
					// attacker.receiveDamage(this, attack.calcDamage(), false);
				} else if (isTaitanrock) {
					attack.actionTaitan(0);
					attack.commitTaitan(0);
				} else if (isTaitanBllit) {
					attack.actionTaitan(2);
					attack.commitTaitan(2);
				} else if (isCounterBarrier) {
					attack.actionCounterBarrier();
					attack.commitCounterBarrier();
				} else if (isMortalBody) {
					attack.action();
					attack.commit();

					attack.actionMortalBody();
					attack.commitMortalBody();
					// }else if (isLindArmor){
					// attack.actionLindArmor();
					// attack.commitLindArmor();
				} else {
					attack.action();
					attack.commit();
				}
				attack = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			random = null;
		}
	}
	
	@Override
	public void onAction(L1PcInstance attacker) {
		
		Random random = new Random();
		try {
			if (!�����) {
				if (attacker == null) {
					return;
				}
				if (isTeleport()) {
					return;
				}
				if (CharPosUtil.getZoneType(this) == 1
						|| CharPosUtil.getZoneType(attacker) == 1) {
					L1Attack attack_mortion = new L1Attack(attacker, this);
					attack_mortion.action();
					attack_mortion = null;
					return;
				}

				if (checkNonPvP(this, attacker) == true) {
					L1Attack attack_mortion = new L1Attack(attacker, this);
					attack_mortion.action();
					attack_mortion = null;
					return;
				}
			}
			if (getCurrentHp() > 0 && !isDead()) {
				attacker.delInvis();
				boolean isCounterBarrier = false;
				boolean isTaitanrock = false;

				boolean isTaitanMagic = false;

				boolean isTaitanBllit = false;
				boolean isMortalBody = false;
				boolean isLindArmor = false;
				L1Attack attack = new L1Attack(attacker, this);

				if (attack.calcHit()) { 
					int rock=0;
					int titan = getInventory().checkEquippedcount(30083);
					rock += titan ==1 ? 5:titan == 2 ? 10:0;
				//	System.out.println("Ÿ��ź������ = " + rock);			
					for (L1DollInstance doll : getDollList()) {
						if (doll.getDollType() == L1DollInstance.DOLLTYPE_�����̾�)
							rock+=5;		
					}
				//	System.out.println("���������� = " + rock);		
					if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.Ÿ��ź����¡)){ //Ÿ��ź����¡
						int r = 5;
						if(getLevel() > 80)
							r += getLevel()-80;
						if(rock > 10)
							r = 10;	
						
						rock += r;

					}
				//	System.out.println("����¡������ = " + rock);						
					if (attacker.getWeapon() != null) {
						if (isTaitanM
								&& attacker.getWeapon().getItem().getType() == 17) {
							int hpRatio = 100;
							if (0 < getMaxHp()) {
								hpRatio = 100 * getCurrentHp() / getMaxHp();
							}
							if (hpRatio < 41 + rock) {
								int chan = random.nextInt(100) + 1;
								boolean isProbability = false;
								if (getInventory().checkItem(41246, 10)) {
									if (25 > chan) {
										isProbability = true;
									}
								}
								if (getSkillEffectTimerSet().hasSkillEffect(
										L1SkillId.SHOCK_STUN)
										|| getSkillEffectTimerSet()
												.hasSkillEffect(
														L1SkillId.EARTH_BIND)) {
									isProbability = false;
								}
								if (isProbability) {
									getInventory().consumeItem(41246, 10);
									isTaitanMagic = true;
								}
							}

						}
						// }else{
						if (isTaitanR) {
							if (attacker.getWeapon().getItem().getType() != 4
									&& attacker.getWeapon().getItem().getType() != 10
									&& attacker.getWeapon().getItem().getType() != 17
									&& attacker.getWeapon().getItem().getType() != 13) {
								int hpRatio = 100;
								if (0 < getMaxHp()) {
									hpRatio = 100 * getCurrentHp() / getMaxHp();
								}
								if (hpRatio < 41 + rock) {
									int chan = random.nextInt(100) + 1;
									boolean isProbability = false;
									if (getInventory().checkItem(41246, 10)) {
										if (25 > chan) {
											isProbability = true;

										}
									}

									if (getSkillEffectTimerSet()
											.hasSkillEffect(
													L1SkillId.SHOCK_STUN)
											|| getSkillEffectTimerSet()
													.hasSkillEffect(
															L1SkillId.EARTH_BIND)) {
										isProbability = false;
									}
									boolean isShortDistance = attack
											.isShortDistance();
									if (isProbability && isShortDistance) {
										isTaitanrock = true;
										getInventory().consumeItem(41246, 10);
									}
								}
							}
						}
						if (isTaitanB) {
							if (attacker.getWeapon() != null) {
								if (attacker.getWeapon().getItem().getType() == 4
										|| attacker.getWeapon().getItem()
												.getType() == 10
										|| attacker.getWeapon().getItem()
												.getType() == 13) {
									int hpRatio = 100;
									if (0 < getMaxHp()) {
										hpRatio = 100 * getCurrentHp()
												/ getMaxHp();
									}
									if (hpRatio < 41 + rock) {
										int chan = random.nextInt(100) + 1;
										boolean isProbability = false;
										if (getInventory().checkItem(41246, 10)) {
											if (25 > chan) {
												isProbability = true;
												getInventory().consumeItem(
														41246, 10);
											}
										}
										if (getSkillEffectTimerSet()
												.hasSkillEffect(
														L1SkillId.SHOCK_STUN)
												|| getSkillEffectTimerSet()
														.hasSkillEffect(
																L1SkillId.EARTH_BIND)) {
											isProbability = false;
										}
										if (isProbability) {
											isTaitanBllit = true;
										}
									}
								}
							}
						}
						// }
					}

					if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.COUNTER_BARRIER)
							&& attacker.getWeapon() != null
							&& attacker.getWeapon().getItem().getType() != 17) {
						if (getWeapon() != null
								&& getWeapon().getItem().getType1() == 50) {
							int chan = random.nextInt(100) + 1;
							boolean isProbability = false;
							if (18 > chan) {
								isProbability = true;
							}
							boolean isShortDistance = attack.isShortDistance();
							if (isProbability && isShortDistance) {
								isCounterBarrier = true;
							}
						}
					} else if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.MORTAL_BODY) /*
													 * && attacker.getWeapon()
													 * != null &&
													 * attacker.getWeapon
													 * ().getItem().getType() !=
													 * 17
													 */) {
						int chan = random.nextInt(100) + 1;
						boolean isProbability = false;
						if (20 > chan) {
							isProbability = true;
						}
						// boolean isShortDistance = attack.isShortDistance();
						if (isProbability /* && isShortDistance */) {
							isMortalBody = true;
						}
					} else {
						/*
						 * for(L1ItemInstance item : getInventory().getItems()){
						 * if(item.isEquipped() && item.getItemId() >= 420108 &&
						 * item.getItemId() <= 420111){ if(attacker.getWeapon()
						 * != null){
						 * if(attacker.getWeapon().getItem().getType1() == 20 ||
						 * attacker.getWeapon().getItem().getType1() == 62){ int
						 * chan = random.nextInt(100);
						 * if(item.getEnchantLevel()*2 > chan){ isLindArmor =
						 * true; } } } break; } } /* int chan =
						 * random.nextInt(100)+1; if(15 > chan){
						 * if(attacker.getWeapon() != null){
						 * if(attacker.getWeapon().getItem().getType1() == 20 ||
						 * attacker.getWeapon().getItem().getType1() == 62){
						 * isLindArmor = true; } } }
						 */
					}
					if (!isTaitanBllit && !isTaitanrock && !isCounterBarrier/*
																			 * &&
																			 * !
																			 * isMortalBody
																			 */
							&& !isLindArmor) {
						// attacker.setPetTarget(this);
						attack.calcDamage();
						attack.calcStaffOfMana();
						attack.calcDrainOfHp();
						attack.addPcPoisonAttack(attacker, this);
					}
				}
				if (isTaitanMagic) {
					attack.actionTaitan(1);
					attack.commitTaitan(0);
					// attack.actionTaitan(1);
					// attacker.receiveDamage(this, attack.calcDamage(), false);
				} else if (isTaitanrock) {
					attack.actionTaitan(0);
					attack.commitTaitan(0);
				} else if (isTaitanBllit) {
					attack.actionTaitan(2);
					attack.commitTaitan(2);
				} else if (isCounterBarrier) {
					attack.actionCounterBarrier();
					attack.commitCounterBarrier();
				} else if (isMortalBody) {
					attack.action();
					attack.commit();

					attack.actionMortalBody();
					attack.commitMortalBody();
					// attack.commit();
					// }else if (isLindArmor){
					// attack.actionLindArmor();
					// attack.commitLindArmor();
				} else {
					attack.action();
					attack.commit();
				}
				attack = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			random = null;
		}
	}

	public boolean checkNonPvP(L1PcInstance pc, L1Character target) {
		L1PcInstance targetpc = null;

		if (target instanceof L1PcInstance)
			targetpc = (L1PcInstance) target;
		else if (target instanceof L1PetInstance)
			targetpc = (L1PcInstance) ((L1PetInstance) target).getMaster();
		else if (target instanceof L1SummonInstance)
			targetpc = (L1PcInstance) ((L1SummonInstance) target).getMaster();

		if (targetpc == null)
			return false;

		if (!Config.ALT_NONPVP) {
			if (getMap().isCombatZone(getLocation()))
				return false;

			for (L1War war : L1World.getInstance().getWarList()) {
				if (pc.getClanid() != 0 && targetpc.getClanid() != 0) {
					boolean same_war = war.CheckClanInSameWar(pc.getClanname(),
							targetpc.getClanname());

					if (same_war == true)
						return false;
				}
			}

			if (target instanceof L1PcInstance) {
				L1PcInstance targetPc = (L1PcInstance) target;
				if (isInWarAreaAndWarTime(pc, targetPc))
					return false;
			}
			return true;
		}

		return false;
	}

	private boolean isInWarAreaAndWarTime(L1PcInstance pc, L1PcInstance target) {
		int castleId = L1CastleLocation.getCastleIdByArea(pc);
		int targetCastleId = L1CastleLocation.getCastleIdByArea(target);
		if (castleId != 0 && targetCastleId != 0 && castleId == targetCastleId) {
			if (WarTimeController.getInstance().isNowWar(castleId)) {
				return true;
			}
		}
		return false;
	}

	public void setPetTarget(L1Character target) {
		L1PetInstance pets = null;
		L1SummonInstance summon = null;
		for (Object pet : getPetList()) {
			if (pet instanceof L1PetInstance) {
				pets = (L1PetInstance) pet;
				pets.setMasterTarget(target);
			} else if (pet instanceof L1SummonInstance) {
				summon = (L1SummonInstance) pet;
				summon.setMasterTarget(target);
			}
		}
	}

	public void delInvis() {
		if (isGmInvis())
			return;
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.INVISIBILITY)) {
			getSkillEffectTimerSet().killSkillEffectTimer(
					L1SkillId.INVISIBILITY);
			S_Invis iv = new S_Invis(getId(), 0);
			sendPackets(iv);
			Broadcaster.broadcastPacket(this, iv, true);

			for (L1PcInstance pc2 : L1World.getInstance()
					.getVisiblePlayer(this)) {
				pc2.sendPackets(new S_OtherCharPacks(this, pc2));
			}

			if (isInParty()) {
				for (L1PcInstance tar : L1World.getInstance().getVisiblePlayer(
						this, -1)) {
					if (getParty().isMember(tar)) {
						tar.sendPackets(new S_HPMeter(this));
					}
				}
			}

		}
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BLIND_HIDING)) {
			getSkillEffectTimerSet().killSkillEffectTimer(
					L1SkillId.BLIND_HIDING);
			 if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ؽ�)) 
				 getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ؽ�);	
			S_Invis iv = new S_Invis(getId(), 0);
			sendPackets(iv);
			Broadcaster.broadcastPacket(this, iv, true);
			for (L1PcInstance pc2 : L1World.getInstance()
					.getVisiblePlayer(this)) {
				pc2.sendPackets(new S_OtherCharPacks(this, pc2));
			}
			if (isInParty()) {
				for (L1PcInstance tar : L1World.getInstance().getVisiblePlayer(
						this, 20)) {
					if (getParty().isMember(tar)) {
						tar.sendPackets(new S_HPMeter(this));
					}
				}
			}
		}

	}

	public void delBlindHiding() {
		getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.BLIND_HIDING);
		S_Invis iv = new S_Invis(getId(), 0);
		sendPackets(iv);
		Broadcaster.broadcastPacket(this, iv, true);
		// broadcastPacket(new S_OtherCharPacks(this));
	}

	public void receiveDamage(L1Character attacker, int damage, int attr) {
		Random random = new Random(System.nanoTime());
		int player_mr = getResistance().getEffectedMrBySkill();
		int rnd = random.nextInt(100) + 1;
		if (player_mr >= rnd) {
			damage /= 2;
		}
		random = null;

		receiveDamage(attacker, damage, false);
	}

	public void receiveManaDamage(L1Character attacker, int mpDamage) {
		if (mpDamage > 0 && !isDead()) {
			delInvis();
			if (attacker instanceof L1PcInstance) {
				L1PinkName.onAction(this, attacker);
			}
			/*
			 * if (attacker instanceof L1PcInstance && ((L1PcInstance)
			 * attacker).isPinkName()) { L1GuardInstance guard = null; for
			 * (L1Object object :
			 * L1World.getInstance().getVisibleObjects(attacker)) { if (object
			 * instanceof L1GuardInstance) { guard = (L1GuardInstance) object;
			 * guard.setTarget(((L1PcInstance) attacker)); } } }
			 */

			int newMp = getCurrentMp() - mpDamage;
			this.setCurrentMp(newMp);
		}
	}

	public long _oldTime = 0;

	public boolean Auto_check = false;

	public synchronized void receiveDamage(L1Character attacker, double damage,
			boolean isMagicDamage) {
		if (isTeleport() || isGhost() || isGmInvis()) {
			return;
		}
		Random random = new Random(); // ��Ǫ��ȣ
		try {
			if (getCurrentHp() > 0 && !isDead()) {
				if (attacker != this
						&& !getNearObjects().knownsObject(attacker)
						&& attacker.getMapId() == this.getMapId()) {
					attacker.onPerceive(this);
				}
				if (damage >= 0) {
					int chance = random.nextInt(100);
					int plus_hp = 50 + random.nextInt(20);
					if (getInventory().checkEquipped(420104)
							|| getInventory().checkEquipped(420105)
							|| getInventory().checkEquipped(420106)
							|| getInventory().checkEquipped(420107)) {
						if (chance <= 9 && getCurrentHp() != getMaxHp()) {
							setCurrentHp(getCurrentHp() + plus_hp);
							// sendPackets(new S_SkillSound(getId(), 2187));
							// Broadcaster.broadcastPacket(this, new
							// S_SkillSound(getId(), 2187));
						}
					}
				}
				if (damage > 0) {
					// ������ �����°� �׽�Ʈ�ؾߵ�.
					if (this instanceof L1RobotInstance) {
						L1RobotInstance bot = (L1RobotInstance) this;
						if (bot.�̵������� == 0) {
							int sleepTime = bot
									.calcSleepTime(L1RobotInstance.DMG_MOTION_SPEED);
						//	bot.�̵������� = sleepTime;
						}
						/*if (attacker instanceof L1PcInstance) {
							if (!isParalyzed() && !isSleeped()) {
								if (bot.�ڻ�� && bot.getMap().isTeleportable())
									bot.������();
								else {
									if (!bot.Ÿ�ݱ�ȯ����) {
										if (bot.��ɺ�_��ġ.startsWith("����"))
											bot.������(60000 + _random
													.nextInt(60000));
										bot.��ȯ();
									}
								}
							}
						} else 
							
							
							
							if (attacker instanceof L1MonsterInstance) {
							if (attacker.getMaxHp() >= 6000)
								bot.��ȯ();
						}*/
					}
					delInvis();
					// ���� ���� ������ ����...
					if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.ICE_LANCE)) {
						return;
					}
					if (attacker instanceof L1PcInstance) {
						if (L1PcInstance.this != attacker)
							L1PinkName.onAction(this, attacker);
						((L1PcInstance) attacker).setPetTarget(this);
					} else if (attacker instanceof L1NpcInstance) {
						L1NpcInstance npc = (L1NpcInstance) attacker;
						if (npc.getNpcTemplate().is_doppel()) {
							L1PinkName.onAction(this, attacker);
						}

						if (isMagicDamage
								&& (npc.getNpcId() == 4038000
										|| npc.getNpcId() == 4200010
										|| npc.getNpcId() == 4200011
										|| npc.getNpcId() == 4039000
										|| npc.getNpcId() == 4039006
										|| npc.getNpcId() == 4039007 || (npc
										.getNpcId() >= 100663 && npc.getNpcId() <= 100668))) {
							S_DoActionGFX ������ = new S_DoActionGFX(getId(),
									ActionCodes.ACTION_Damage);
							sendPackets(������);
							Broadcaster.broadcastPacket(this, ������, true);
						}
					}
					/*
					 * if (attacker instanceof L1PcInstance && ((L1PcInstance)
					 * attacker).isPinkName()) { for (L1Object object :
					 * L1World.getInstance().getVisibleObjects(attacker)) { if
					 * (object instanceof L1GuardInstance) { L1GuardInstance
					 * guard = (L1GuardInstance) object;
					 * guard.setTarget(((L1PcInstance) attacker)); } } }
					 */
					if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.FOG_OF_SLEEPING)) {
						getSkillEffectTimerSet().removeSkillEffect(
								L1SkillId.FOG_OF_SLEEPING);
					} else if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.PHANTASM)) {
						getSkillEffectTimerSet().removeSkillEffect(
								L1SkillId.PHANTASM);
					} else if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.DARK_BLIND)) {
						getSkillEffectTimerSet().removeSkillEffect(
								L1SkillId.DARK_BLIND);
					}
				}
				if (damage > 0) {
					if (getInventory().checkEquipped(145)
							|| getInventory().checkEquipped(149)) {
						damage *= 1.5;
					}
				}
				if (�����) {
					��Ż������ += (int) (damage);
					S_ChatPacket s_chatpacket = new S_ChatPacket(this, "[H:"
							+ Ÿ�� + " M:" + �̽� + " T:" + ���� + "] DMG : "
							+ (int) (damage) + " / TotalDMG : " + ��Ż������,
							Opcodes.S_SAY, 0);
					Broadcaster.broadcastPacket(this, s_chatpacket, true);

					return;
				}
				int newHp = getCurrentHp() - (int) (damage);
				if (newHp > getMaxHp()) {
					newHp = getMaxHp();
				}
				if(newHp <=10 && getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�ҿ�踮��)){
					int s = (int) (getCurrentMp() - damage);
					if(s <= 0){
						newHp = 0;
					}else{
						newHp = 10;
						this.setCurrentMp(s);
						sendPackets(new S_SkillSound(getId(), 14541));
						broadcastPacket(new S_SkillSound(getId(), 14541));
					}
				}
				if (newHp <= 0) {
					if (isGm() || isSGm()) {
						this.setCurrentHp(getMaxHp());
					} else {
						if (attacker instanceof L1PcInstance) {
							((L1PcInstance) attacker).sendPackets((new S_SkillSound(attacker.getId(), 6354)));
							Broadcaster.broadcastPacket(attacker, new S_SkillSound(attacker.getId(), 6354));
						if (CharPosUtil.getZoneType(L1PcInstance.this) == 0)
							if (getLevel() >= 50) {
								((L1PcInstance) attacker).set_PKcount(((L1PcInstance) attacker).get_PKcount()+1);
								attacker.setKills(attacker.getKills() + 1);
								setDeaths(getDeaths() + 1);
								sendPackets(new S_PacketBox(S_PacketBox.char_ER, get_PlusEr()),true);
							}
						
						if (getInventory().checkItem(41159, 50)) { //�κ��� ��50�� �ֳ�Ȯ��
				               attacker.getInventory().storeItem(41159, 50); //�¸��� ��50�� �޾ƿ���
				               getInventory().consumeItem(41159, 50); //�й��� �� 50�� ����
				               sendPackets(new S_SystemMessage("\\fY�������� �й��Ͽ� ����(50)�� �Ҿ����ϴ�."));
				               } else if(getInventory(). checkItem(40308, 500000)){//50�� üũ
				            		  getInventory().consumeItem(40308, 500000); //50�� �������
				            		  attacker.getInventory().storeItem(41159, 0);
				            		  attacker.getInventory().storeItem(40308, 500000); //������ ������ �Ҵ´� 
				            		  sendPackets(new S_SystemMessage("������ �����Ͽ� �Ƶ� 50���� �Ҿ����ϴ�"));
                            } else {
                         	   sendPackets(new S_SystemMessage("������ ��� ������ �����ϴ�."));
                            }
						
						int price = getHuntPrice();
						if (CharPosUtil.getZoneType(L1PcInstance.this) == 0) // �����
							if (getHuntCount() > 0) {
								huntoptionminus(this);
								attacker.getInventory().storeItem(40308,price);
								setHuntCount(0);
								setHuntPrice(0);
								setReasonToHunt(null);
								initBeWanted();
								sendMessage("\\fU"+attacker.getName() + "���� " + this.getName() + "���� �׿� ������� �޾ҽ��ϴ�.");
								sendPackets(new S_SystemMessage("\\fY���谡 Ǯ�� �߰� �ɼ��� ��������ϴ�."));
								try {
									save();
								} catch (Exception e) {
									_log.log(Level.SEVERE, "L1PcInstance[]Error", e);
								}
							} else {
								if (CharPosUtil.getZoneType(this) == 0 && CharPosUtil.getZoneType(attacker) == 0) {
									L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("["+attacker.getName()+ "]���� [" +this.getName()+"] ��[��] �׿����ϴ�." ));  
								}
							}
						}
					
						/**��Ʋ��**/
						/** 2011.05.08 ������ ��Ʋ�� */
		 {
							death(attacker);
						}
					}
				}
				if (newHp > 0) {
					this.setCurrentHp(newHp);
				}
			} else if (!isDead()) {
				System.out
						.println("[L1PcInstance] ������÷��̾��� HP���� ó���� �ùٸ��� �������� ���� ���Ұ� �ֽ��ϴ�.��Ȥ�� ���ʺ��� HP0");
				death(attacker);
			} else if (isDead() && getCurrentHp() > 0) {
				System.out
						.println("[L1PcInstance] ��� : ���� ��� ����HP 0 �̻�. ������ ����. ĳ����: "
								+ getName() + " ������: " + attacker != null ? attacker
								.getName() : "����");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			random = null;
		}
	}
	
	private void sendMessage(String msg) {
		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			pc.sendPackets(new S_ChatPacket(pc, msg, Opcodes.S_MESSAGE, 18));
		}
	}

	public void death(L1Character lastAttacker) {
		synchronized (this) {
			if (isDead()) {
				return;
			}
			setDead(true);
			setActionStatus(ActionCodes.ACTION_Die);
		}
		if (lastAttacker instanceof L1PcInstance) {
			L1PcInstance _atker = (L1PcInstance) lastAttacker;
	
			//�κ� ų
			//	_atker._PlayPcKill++;
			if (CharPosUtil.getZoneType(this) == 0
					&& CharPosUtil.getZoneType(lastAttacker) == 0) {
				if (this instanceof L1RobotInstance) { //�κ��̶��
					if(_atker instanceof L1PcInstance && this.getClanid() != _atker.getClanid()){ //�Ϲ��������
								
				//	_atker.setKills(_atker.getKills() + 1); //�Ϲ����� ų��+1
    						
					 }
					}
				// L1World.getInstance().broadcastServerMessage(lastAttacker.getName()+" �� "
				// +this.getName()+ "�� 14�� ô�߸� �¿�� ��Ʋ�� �����ϴ�.");

				// L1World.getInstance().broadcastServerMessage(lastAttacker.getName()+" �� "
				// +this.getName()+ "�� �׿����ϴ�.",false);

				if (_atker.isInParty()) {
					for (L1PcInstance atker_p : _atker.getParty().getMembers()) {
						atker_p.sendPackets(new S_ServerMessage(3690,
								lastAttacker.getName(), this.getName()), true);
					}
					if (this.isInParty()) {
						for (L1PcInstance defender_p : this.getParty()
								.getMembers()) {
							defender_p.sendPackets(new S_ServerMessage(3689,
									this.getName()), true);
						}
					}
				} else {
					_atker.sendPackets(
							new S_ServerMessage(3691, lastAttacker.getName(),
									this.getName()), true);
				}

				/*
				 * if (this instanceof L1RobotInstance ||
				 * getInventory().checkItem(41159, 50)) { //�κ��� ��50�� �ֳ�Ȯ��
				 * //L1World.getInstance().broadcastServerMessage( //����޼��� �۽� //
				 * getName() + "�� ��ػ��·� " +lastAttacker.getName() +
				 * "���� ���� 50�� ��");//PK�¸��� �޼���ǥ��
				 * L1World.getInstance().broadcastServerMessage( //����޼��� �۽�
				 * getName() + "�� " +lastAttacker.getName() +
				 * "���� ���� 50���� �־����ϴ�.");//PK�¸��� �޼���ǥ��
				 * lastAttacker.getInventory().storeItem(41159, 50); //�¸��� ��50��
				 * �޾ƿ��� if(!(this instanceof L1RobotInstance)){
				 * getInventory().consumeItem(41159, 50); //�й��� �� 50�� ����
				 * S_SystemMessage sm = new
				 * S_SystemMessage("�������� �й��Ͽ� ����(50)�� �Ҿ����ϴ�."); sendPackets(sm);
				 * sm.clear(); sm = null; } }else{//������� ���໯�����
				 * if(getInventory(). checkItem(40308, 500000)){//50�� üũ
				 * getInventory().consumeItem(40308, 500000); //50�� �������
				 * lastAttacker.getInventory().storeItem(41159, 0);
				 * //L1World.getInstance().broadcastServerMessage( // getName()
				 * + "�� �������� ���� " +lastAttacker.getName() + "���� �Ƶ� ��");//PK�¸���
				 * �޼���ǥ�� lastAttacker.getInventory().storeItem(40308, 500000);
				 * //������ ������ �Ҵ´� L1World.getInstance().broadcastServerMessage(
				 * getName() + "�� ������ ���� " +lastAttacker.getName() +
				 * "���� �Ƶ��� �־����ϴ�.");//PK�¸��� �޼���ǥ��
				 * lastAttacker.getInventory().storeItem(40308, 500000); //������
				 * ������ �Ҵ´� S_SystemMessage sm = new
				 * S_SystemMessage("������ �����Ͽ� �Ƶ� 50���� �Ҿ����ϴ�."); sendPackets(sm);
				 * sm.clear(); sm = null; }else{
				 * L1World.getInstance().broadcastServerMessage(getName() +
				 * "�� ���� �� �Ƶ��� ���� ���� �ʾҽ��ϴ�.");//PK�¸��� �޼���ǥ�� } } if
				 * (getHuntCount() > 0){
				 * lastAttacker.getInventory().storeItem(40308, getHuntPrice());
				 * setHuntCount(0); setHuntPrice(0); setReasonToHunt(null);
				 * L1World
				 * .getInstance().broadcastServerMessage(lastAttacker.getName()
				 * + " �� " + getName() + "�� 14�� ô�߸� ��Ʋ�� ����� ȹ��");
				 * initBeWanted(); try { save(); } catch (Exception e) {
				 * _log.log(Level.SEVERE, e.getLocalizedMessage(), e); } }
				 */
			}
		}
		GeneralThreadPool.getInstance().execute(new Death(lastAttacker));

	}

	int stunskillid[] = { SHOCK_STUN, MOB_SHOCKSTUN_30, MOB_RANGESTUN_19,
			MOB_RANGESTUN_18, EARTH_BIND, MOB_COCA, MOB_BASILL, ICE_LANCE,
			FREEZING_BREATH, L1SkillId.�������, BONE_BREAK, PHANTASM,
			FOG_OF_SLEEPING, CURSE_PARALYZE, CURSE_PARALYZE2,
			L1SkillId.STATUS_CURSE_PARALYZED, L1SkillId.STATUS_POISON_PARALYZED };

	private class Death implements Runnable {
		L1Character _lastAttacker;

		Death(L1Character cha) {
			_lastAttacker = cha;
		}

		public void run() {
			L1Character lastAttacker = _lastAttacker;
			_lastAttacker = null;
			setCurrentHp(0);
			setGresValid(false);
			boolean pinklawful = false;
			try {

				try {
					if (getDollList() != null && getDollList().size() > 0) {
						for (L1DollInstance doll : getDollList()) {
							if (doll == null)
								continue;
							doll.deleteDoll();
						}
					}
				} catch (Exception e) {
				}

				int tel_loop_count = 10;
				while (isTeleport() && tel_loop_count-- > 0) {
					Thread.sleep(300);
				}

				// stopHpRegeneration();
				// stopMpRegeneration();

				int targetobjid = getId();
				if(isInParty()){//��Ƽ�߰�
					getParty().memberDie(L1PcInstance.this);
				}
				getMap().setPassable(getLocation(), true);

				if (isPrivateShop()) {
					getSellList().clear();
					getBuyList().clear();
					setPrivateShop(false);
					L1PolyMorph.undoPoly(L1PcInstance.this);
					sendPackets(new S_DoActionGFX(getId(),
							ActionCodes.ACTION_Idle), true);
					Broadcaster
							.broadcastPacket(L1PcInstance.this,
									new S_DoActionGFX(getId(),
											ActionCodes.ACTION_Idle), true);
					setTempCharGfxAtDead(getClassId());
					Thread.sleep(100);
				} else if (isFishing()) {
					setFishingTime(0);
					setFishingReady(false);
					setFishing(false);
					setFishingItem(null);
					sendPackets(new S_CharVisualUpdate(L1PcInstance.this), true);
					Broadcaster.broadcastPacket(L1PcInstance.this,
							new S_CharVisualUpdate(L1PcInstance.this), true);
					FishingTimeController.getInstance().removeMember(
							L1PcInstance.this);
					Thread.sleep(100);
				}

				int tempchargfx = 0;
				if (getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.SHAPE_CHANGE)) {
					//tempchargfx = getGfxId().getTempCharGfx();
					if (tempchargfx == 0 && getClassId() != 0)
						setTempCharGfxAtDead(getClassId());
					else
						setTempCharGfxAtDead(getClassId());
				} else {
					setTempCharGfxAtDead(getClassId());
				}
				tempchargfx = getClassId();

				/*
				 * for (int i = 0; i < stunskillid.length; i++) {
				 * if(getSkillEffectTimerSet().hasSkillEffect(stunskillid[i]))
				 * getSkillEffectTimerSet().removeSkillEffect(stunskillid[i]); }
				 */

				for (int skillNum = L1SkillId.COOKING_BEGIN; skillNum <= L1SkillId.COOKING_END; skillNum++) {
					if (getSkillEffectTimerSet().hasSkillEffect(skillNum))
						getSkillEffectTimerSet().removeSkillEffect(skillNum);
				}
				for (int i = L1SkillId.COOKING_NEW_�ѿ�; i <= L1SkillId.COOKING_NEW_�߰���; i++) {
					if (getSkillEffectTimerSet().hasSkillEffect(i))
						getSkillEffectTimerSet().removeSkillEffect(i);
				}

				for (int i = L1SkillId.���̸����Ѷ��; i <= L1SkillId.���̽ÿ�������; i++) {
					if (getSkillEffectTimerSet().hasSkillEffect(i))
						getSkillEffectTimerSet().removeSkillEffect(i);
				}

				L1SkillUse l1skilluse = new L1SkillUse();
				l1skilluse.handleCommands(L1PcInstance.this,
						L1SkillId.CANCELLATION, getId(), getX(), getY(), null,
						0, L1SkillUse.TYPE_LOGIN);
				l1skilluse = null;

				if (tempchargfx == 5727 || tempchargfx == 5730
						|| tempchargfx == 5733 || tempchargfx == 5736) {
					tempchargfx = 0;
				}
			//	System.out.println(tempchargfx);
				if (tempchargfx != 0) {
					S_ChangeShape cs = new S_ChangeShape(getId(), tempchargfx);
			//		System.out.println("333");
					sendPackets(cs);
					Broadcaster.broadcastPacket(L1PcInstance.this, cs, true);
				} else {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}

				S_DoActionGFX da = new S_DoActionGFX(targetobjid,
						ActionCodes.ACTION_Die);
				sendPackets(da);
				Broadcaster.broadcastPacket(L1PcInstance.this, da, true);

				if (isPinkName()) {
					pinklawful = true;
					setPinkName(false);
					getSkillEffectTimerSet().removeSkillEffect(
							L1SkillId.STATUS_PINK_NAME);
				}

				if (lastAttacker != L1PcInstance.this) {
					if (CharPosUtil.getZoneType(L1PcInstance.this) != 0) {
						L1PcInstance player = null;
						if (lastAttacker instanceof L1PcInstance) {
							player = (L1PcInstance) lastAttacker;
						} else if (lastAttacker instanceof L1PetInstance) {
							player = (L1PcInstance) ((L1PetInstance) lastAttacker)
									.getMaster();
						} else if (lastAttacker instanceof L1SummonInstance) {
							player = (L1PcInstance) ((L1SummonInstance) lastAttacker)
									.getMaster();
						}
						if (player != null) {
							if (!isInWarAreaAndWarTime(L1PcInstance.this,
									player)) {
								return;
							}
						}
					}

					boolean sim_ret = simWarResult(lastAttacker);
					if (sim_ret == true) {
						return;
					}
				}

				if (CharPosUtil.getZoneType(L1PcInstance.this) == 1) {
					sendPackets(new S_ServerMessage(3805));
					return;
				}

				if (!getMap().isEnabledDeathPenalty()) {
					sendPackets(new S_ServerMessage(3805));
					return;
				}

				L1PcInstance fightPc = null;
				if (lastAttacker instanceof L1PcInstance) {
					fightPc = (L1PcInstance) lastAttacker;
				}
				if (fightPc != null) {
					if (getFightId() == fightPc.getId()
							&& fightPc.getFightId() == getId()) {
						setFightId(0);
						S_PacketBox pb = new S_PacketBox(S_PacketBox.MSG_DUEL,
								0, 0);
						sendPackets(pb, true);
						fightPc.setFightId(0);
						fightPc.sendPackets(pb);
						return;
					}
				}

				boolean castle_ret = castleWarResult();
				if (castle_ret == true) {
					return;
				}
				

				if (getInventory().checkEquipped(222501) && (getMapId() == 1700 || getMapId() == 1703)) { // ������ ��ȣ
					drop��ȣ(getInventory().findItemId(222501));
					return;
				}
		
				if (Config.�Ƴ���̺�Ʈ){
					if (lastAttacker instanceof L1PcInstance) {
						if (getInventory().checkEquipped(21276)) { // ������ ������
							drop();	return;
						}
					} else {
						int chance = _random.nextInt(100);
						if (chance < 40){
							if (getInventory().checkEquipped(21276)) { // ������ ������
								drop();	return;
							}
						}
					} 
				}
				
				// 65���� �����϶� 10������ �������� ������ ���ϸ� �г�Ƽ ����.
				//if (lastAttacker instanceof L1PcInstance && getLevel() < 65 && (lastAttacker.getLevel() - getLevel()) >= 10) {
				//	sendPackets(new S_SystemMessage("\\fW������ ��ȣ�� ���� ����ġ�� �������ʽ��ϴ�."));
				//	return;
				//} else if (getLevel() > 51) {
				//	deathPenalty();
				//}
				/*
				 * if (getLevel() >= 70 && getLevel() < 75) { initLevelBonus();
				 * setLevelBonus(lvl70); addLevelBonus(); } else if (getLevel()
				 * >= 75 && getLevel() < 79) { initLevelBonus();
				 * setLevelBonus(lvl75); addLevelBonus(); } else if (getLevel()
				 * >= 80 && getLevel() < 84) { initLevelBonus();
				 * setLevelBonus(lvl80); addLevelBonus(); } else if (getLevel()
				 * >= 85 && getLevel() < 90) { initLevelBonus();
				 * setLevelBonus(lvl85); addLevelBonus(); } else if (getLevel()
				 * >= 90 && getLevel() < 95) { initLevelBonus();
				 * setLevelBonus(lvl90); addLevelBonus(); } else if (getLevel()
				 * >= 95) { initLevelBonus(); setLevelBonus(lvl95);
				 * addLevelBonus(); }
				 */

				deathPenalty();
				setGresValid(true);

				if (!isFirstBlood()) {
					 sendPackets(new S_SystemMessage("ù ������ ���� �ϼ̽��ϴ�."));
					setFirstBlood(true);
				}

				if (getExpRes() == 0) {
					setExpRes(1);
				}
				if (lastAttacker instanceof L1PcInstance) {
					L1PcInstance t = (L1PcInstance) lastAttacker;
					t.sendPackets(new S_PacketBox(S_PacketBox.��Ʋ��,
							L1PcInstance.this.getId()), true);
				}
				/** �κ� ���� ��Ʈ **/
				if(lastAttacker instanceof L1RobotInstance){
					Random dierandom = new Random();
					L1PcInstance lastrob = (L1RobotInstance) lastAttacker;
					diement = _diementArray[dierandom.nextInt(_diementArray.length)];
					try{
					Delay(1000);
					Broadcaster.broadcastPacket(lastrob,new S_ChatPacket(lastrob, diement, Opcodes.S_SAY, 0));
					diement = null;
					} catch(Exception e){
					}
					}
				/** �κ� ���� ��Ʈ **/
				/** �κ� ���� ��Ʈ **/
				//if (lastAttacker instanceof L1GuardInstance) {
				//	if (get_PKcount() > 0) {
				//		set_PKcount(get_PKcount() - 1);
				//	}
				//	setLastPk(null);
				//}

				/*
				 * int lostRate = (int) (((getLawful() + 32768D) / 1000D - 65D)
				 * * 5D); if (lostRate < 0) { lostRate *= -1; if (getLawful() <
				 * 0) { lostRate *= 2; } Random random = new Random(); int rnd =
				 * random.nextInt(1000) + 1;
				 * 
				 * if (rnd <= lostRate) { int count = 1; if (getLawful() <=
				 * -30000) { count = random.nextInt(5) + 1; } else if
				 * (getLawful() <= -20000) { count = random.nextInt(4) + 1; }
				 * else if (getLawful() <= -10000) { count = random.nextInt(3) +
				 * 1; } else if (getLawful() < 0) { count = random.nextInt(2) +
				 * 1; } else if (getLawful() < 10000) { count =
				 * random.nextInt(3) + 1; } else if (getLawful() < 20000) {
				 * count = random.nextInt(2) + 1; } else if (getLawful() <=
				 * 32767) { count = 1; } caoPenaltyResult(count); } }
				 */
				Random random = new Random();
				int rnd = random.nextInt(100) + 1;
				byte count = 0;
				/*
				 * if (getLawful() < -30000) { if(rnd <= 80) count = (byte)
				 * (random.nextInt(6) + 1); }else if (getLawful() < -25000) {
				 * if(rnd <= 75) count = (byte) (random.nextInt(6) + 1); }else
				 * if (getLawful() < -15000) { if(rnd <= 70) count = (byte)
				 * (random.nextInt(5) + 1); }else if (getLawful() < -5000) {
				 * if(rnd <= 65) count = (byte) (random.nextInt(5) + 1); }else
				 * if (getLawful() < 0) { if(rnd <= 60) count = (byte)
				 * (random.nextInt(4) + 1); }else if (getLawful() == 0) { if(rnd
				 * <= 55) count = (byte) (random.nextInt(4) + 1); }else if
				 * (getLawful() <= 5000) { if(rnd <= 50) count = (byte)
				 * (random.nextInt(3) + 1); }else if (getLawful() <= 15000) {
				 * if(rnd <= 43) count = (byte) (random.nextInt(2) + 1); }else
				 * if (getLawful() <= 25000) { if(rnd <= 35) count = (byte)
				 * (random.nextInt(2) + 1); }else if (getLawful() <= 30000) {
				 * if(rnd <= 30) count = 1; }else if (getLawful() < 32767) {
				 * if(rnd <= 20) count = 1; }else if (getLawful() <= 32767){
				 * if(rnd <= 15) count = 1; }
				 */

				if (getLawful() < -20000) {
					if (rnd <= 70)
						count = (byte) (random.nextInt(3) + 2);
					// sendPackets(new
					// S_SystemMessage("��ī�� ~ -20000 Ȯ�� 90 ����:"+count));
				} else if (getLawful() < -7000) {
					if (rnd <= 60)
						count = (byte) (random.nextInt(3) + 1);
					// sendPackets(new
					// S_SystemMessage("-20001 ~ -7000 Ȯ�� 60 ����:"+count));
				} else if (getLawful() < 0) {
					if (rnd <= 55)
						count = (byte) (random.nextInt(3) + 1);
					// sendPackets(new
					// S_SystemMessage("-7001 ~ 7000 Ȯ�� 50 ����:"+count));
				} else if (getLawful() <= 7000) {
					if (rnd <= 50)
						count = (byte) (random.nextInt(3) + 1);
					// sendPackets(new
					// S_SystemMessage("-7001 ~ 7000 Ȯ�� 50 ����:"+count));
				} else if (getLawful() <= 15000) {
					if (rnd <= 40)
						count = (byte) (random.nextInt(3) + 1);
					// sendPackets(new
					// S_SystemMessage("7000 ~ 19999 Ȯ�� 30 ����:"+count));
				} else if (getLawful() <= 23000) {
					if (rnd <= 30)
						count = (byte) (random.nextInt(3) + 1);
					// sendPackets(new
					// S_SystemMessage("20000 ~ 32766 Ȯ�� 10 ����:"+count));
				} else if (getLawful() <= 28000) {
					if (rnd <= 1)
						count = 0;// count = 0;
					// sendPackets(new S_SystemMessage("�����Ǯ �ȶ��� ����:"+count));
				}
				// if(rnd <= 80) count = (byte) (random.nextInt(2) + 1);
				random = null;
				if (count != 0) {
					caoPenaltyResult(count);
				}

				L1PcInstance player = null;
				if (lastAttacker instanceof L1PcInstance) {
					player = (L1PcInstance) lastAttacker;
				}
				if (player != null) {
					if (pinklawful == false && getLawful() >= 0) {
						// boolean isChangePkCount = false;
						
						 //if (player.getLawful() < 30000) {
						//	 player.set_PKcount(player.get_PKcount() + 1);
						//	 isChangePkCount = true; 
						//	 player.setLastPk();
						 //}

						int lawful;
						if ((player.getLevel() - 10 > getLevel() && getLawful() >= 0)
								|| player.getLevel() + 10 < getLevel())
							lawful = -32768;
						else
							lawful = player.getLawful() - 10000;

						if (lawful <= -32768)
							lawful = -32768;
						player.setLawful(lawful);

						S_Lawful s_lawful = new S_Lawful(player.getId(),player.getLawful());
						player.sendPackets(s_lawful);
						Broadcaster.broadcastPacket(player, s_lawful, true);
						// ���� �ý������� �ּ�
						
						//if (isChangePkCount && player.get_PKcount() >= 5 && player.get_PKcount() < 100) { 
						//	player.sendPackets(new S_BlueMessage(551, String.valueOf(player.get_PKcount()), "100")); 
						//} else if (isChangePkCount && player.get_PKcount() >= 100) {
						//	player.beginHell(true); 
						//}
						
					//} else {
					//	if (isPinkName()){ //76762626 //
					//		sendPackets(new S_SystemMessage("���� �������ϴ� 376 ����"));
					//		setPinkName(false);
					//		getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_PINK_NAME); 
					//	}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void caoPenaltyResult(int count) { //����������
		// /if(getAccessLevel() == Config.GMCODE || count == 0){
		// / return;
		// /}
		for (int i = 0; i < count; i++) {
			L1ItemInstance item = getInventory().CaoPenalty();
			if (item != null) {
				if (item.getBless() > 3) {
					getInventory().removeItem(item,
							item.isStackable() ? item.getCount() : 1);
					sendPackets(new S_ServerMessage(158, item.getLogName())); // \f1%0%s
					// ���ߵǾ�
					// ������ϴ�.
				} else {
					getInventory().tradeItem(
							item,
							item.isStackable() ? item.getCount() : 1,
									L1World.getInstance().getInventory(getX(), getY(),
											getMapId()));
					sendPackets(new S_ServerMessage(638, item.getLogName())); // %0��
					// �Ҿ����ϴ�

				}
		}
	}
	}

	/*
	 * private void caoPenaltySkill(int count) { int l = 0; int lv1 = 0; int lv2
	 * = 0; int lv3 = 0; int lv4 = 0; int lv5 = 0; int lv6 = 0; int lv7 = 0; int
	 * lv8 = 0; int lv9 = 0; int lv10 = 0; Random random = new Random(); int
	 * lostskilll = 0; for (int i = 0; i < count; i++) { if (isCrown()) {
	 * lostskilll = random.nextInt(16) + 1; } else if (isKnight()) { lostskilll
	 * = random.nextInt(8) + 1; } else if (isElf()) { lostskilll =
	 * random.nextInt(48) + 1; } else if (isDarkelf()) { lostskilll =
	 * random.nextInt(23) + 1; } else if (isWizard()) { lostskilll =
	 * random.nextInt(80) + 1; }
	 * 
	 * if (!SkillsTable.getInstance().spellCheck(getId(), lostskilll)) { random
	 * = null; return; }
	 * 
	 * L1Skills l1skills = null; l1skills =
	 * SkillsTable.getInstance().getTemplate(lostskilll); if
	 * (l1skills.getSkillLevel() == 1) {lv1 |= l1skills.getId();} if
	 * (l1skills.getSkillLevel() == 2) {lv2 |= l1skills.getId();} if
	 * (l1skills.getSkillLevel() == 3) {lv3 |= l1skills.getId();} if
	 * (l1skills.getSkillLevel() == 4) {lv4 |= l1skills.getId();} if
	 * (l1skills.getSkillLevel() == 5) {lv5 |= l1skills.getId();} if
	 * (l1skills.getSkillLevel() == 6) {lv6 |= l1skills.getId();} if
	 * (l1skills.getSkillLevel() == 7) {lv7 |= l1skills.getId();} if
	 * (l1skills.getSkillLevel() == 8) {lv8 |= l1skills.getId();} if
	 * (l1skills.getSkillLevel() == 9) {lv9 |= l1skills.getId();} if
	 * (l1skills.getSkillLevel() == 10) {lv10 |= l1skills.getId();}
	 * 
	 * SkillsTable.getInstance().spellLost(getId(), lostskilll); l = lv1 + lv2 +
	 * lv3 + lv4 + lv5 + lv6 + lv7 + lv8 + lv9 + lv10; } if (l > 0) { S_DelSkill
	 * ds = new S_DelSkill(lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8, lv9, lv10, 0,
	 * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); sendPackets(ds);
	 * ds.clear(); ds = null; } random = null; }
	 */
	public boolean castleWarResult() {
		if (getClanid() != 0 && isCrown()) {
			L1Clan clan = L1World.getInstance().getClan(getClanname());
			for (L1War war : L1World.getInstance().getWarList()) {
				int warType = war.GetWarType();
				boolean isInWar = war.CheckClanInWar(getClanname());
				boolean isAttackClan = war.CheckAttackClan(getClanname());
				if (getId() == clan.getLeaderId() && warType == 1 && isInWar
						&& isAttackClan) {
					String enemyClanName = war.GetEnemyClanName(getClanname());
					if (enemyClanName != null) {
						if (war.GetWarType() == 1) {// �������ϰ��
							L1PcInstance clan_member[] = clan
									.getOnlineClanMember();//
							int castle_id = war.GetCastleId();
							int[] loc = new int[3];
							loc = L1CastleLocation.getGetBackLoc(castle_id);
							int locx = loc[0];
							int locy = loc[1];
							short mapid = (short) loc[2];
							for (int k = 0; k < clan_member.length; k++) {
								if (L1CastleLocation.checkInWarArea(castle_id,
										clan_member[k])) {// �⳻�� �ִ� ���� ���� �ڷ���Ʈ
									L1Teleport.teleport(clan_member[k], locx,
											locy, mapid, 5, true);
								}
							}
							loc = null;
						}
						war.CeaseWar(getClanname(), enemyClanName); // ����
					}
					break;
				}
			}
		}

		int castleId = 0;
		boolean isNowWar = false;
		castleId = L1CastleLocation.getCastleIdByArea(this);
		if (castleId != 0) {
			isNowWar = WarTimeController.getInstance().isNowWar(castleId);
		}
		return isNowWar;
	}

	public boolean simWarResult(L1Character lastAttacker) {
		if (getClanid() == 0) {
			return false;
		}
		if (Config.SIM_WAR_PENALTY) {
			return false;
		}
		L1PcInstance attacker = null;
		String enemyClanName = null;
		boolean sameWar = false;

		if (lastAttacker instanceof L1PcInstance) {
			attacker = (L1PcInstance) lastAttacker;
		} else if (lastAttacker instanceof L1PetInstance) {
			attacker = (L1PcInstance) ((L1PetInstance) lastAttacker)
					.getMaster();
		} else if (lastAttacker instanceof L1SummonInstance) {
			attacker = (L1PcInstance) ((L1SummonInstance) lastAttacker)
					.getMaster();
		} else {
			return false;
		}
		L1Clan clan = null;
		for (L1War war : L1World.getInstance().getWarList()) {
			clan = L1World.getInstance().getClan(getClanname());

			int warType = war.GetWarType();
			boolean isInWar = war.CheckClanInWar(getClanname());
			if (attacker != null && attacker.getClanid() != 0) {
				sameWar = war.CheckClanInSameWar(getClanname(),
						attacker.getClanname());
			}

			if (getId() == clan.getLeaderId() && warType == 2
					&& isInWar == true) {
				enemyClanName = war.GetEnemyClanName(getClanname());
				if (enemyClanName != null) {
					war.CeaseWar(getClanname(), enemyClanName);
				}
			}

			if (warType == 2 && sameWar) {
				return true;
			}
		}
		return false;
	}

	public void resExp() {
		int oldLevel = getLevel();
		int needExp = ExpTable.getNeedExpNextLevel(oldLevel);
		int exp = 0;
		double ratio;

		if (oldLevel < 45)
			ratio = 0.05;
		else if (oldLevel >= 49)
			ratio = 0.025;
		else
			ratio = 0.05 - (oldLevel - 44) * 0.005;

		exp = (int) (needExp * ratio);

		if (exp == 0)
			return;

		addExp(exp);
	}

	public void resExpTo��ȣ() {
		int oldLevel = getLevel();
		int needExp = ExpTable.getNeedExpNextLevel(oldLevel);
		int exp = 0;
		double ratio = 0;

		if (oldLevel >= 11 && oldLevel < 45)
			ratio = 0.1;
		else if (oldLevel == 45)
			ratio = 0.09;
		else if (oldLevel == 46)
			ratio = 0.08;
		else if (oldLevel == 47)
			ratio = 0.07;
		else if (oldLevel == 48)
			ratio = 0.06;
		else if (oldLevel >= 49)
			ratio = 0.05;

		exp = (int) (needExp * ratio);
		if (exp == 0)
			return;

		int level = ExpTable.getLevelByExp(_exp + exp);
		if (level > Config.MAXLEVEL) {
			S_SystemMessage sm = new S_SystemMessage(
					"���� �������� ����  ���̻� ����ġ�� ȹ���� �� �����ϴ�.");
			sendPackets(sm, true);
			return;
		}

		addExp(exp);
	}

	public void resExpToTemple() {
		int oldLevel = getLevel();
		int needExp = ExpTable.getNeedExpNextLevel(oldLevel);
		int exp = 0;
		double ratio;

		if (oldLevel < 45)
			ratio = 0.05;
		else if (oldLevel >= 45 && oldLevel < 49)
			ratio = 0.05 - (oldLevel - 44) * 0.005;
		else if (oldLevel >= 49 && oldLevel < 52)
			ratio = 0.025;
		else if (oldLevel == 52)
			ratio = 0.026;
		else if (oldLevel > 52 && oldLevel < 74)
			ratio = 0.026 + (oldLevel - 52) * 0.001;
		else if (oldLevel >= 74 && oldLevel < 79)
			ratio = 0.048 - (oldLevel - 73) * 0.0005;
		else
			/* if (oldLevel >= 79) */ratio = 0.0499; // 79������ 4.9%����

		exp = (int) (needExp * ratio);
		if (exp == 0)
			return;

		int level = ExpTable.getLevelByExp(_exp + exp);
		if (level > Config.MAXLEVEL) {
			S_SystemMessage sm = new S_SystemMessage(
					"���� �������� ����  ���̻� ����ġ�� ȹ���� �� �����ϴ�.");
			sendPackets(sm, true);
			return;
		}

		addExp(exp);
	}

	public void deathPenalty() {
		int oldLevel = getLevel();
		int needExp = ExpTable.getNeedExpNextLevel(oldLevel);
		int exp = 0;

		// ���� 30���� ���� �ȵǰ�
		if (oldLevel <= 30)
			return;

		if (oldLevel >= 1 && oldLevel < 11)
			exp = 0;
		else if (oldLevel >= 11 && oldLevel < 45)
			exp = (int) (needExp * 0.1);
		else if (oldLevel == 45)
			exp = (int) (needExp * 0.09);
		else if (oldLevel == 46)
			exp = (int) (needExp * 0.08);
		else if (oldLevel == 47)
			exp = (int) (needExp * 0.07);
		else if (oldLevel == 48)
			exp = (int) (needExp * 0.06);
		else if (oldLevel >= 49)
			exp = (int) (needExp * 0.05);

		if (exp == 0)
			return;

		addExp(-exp);
	}

	public int getbase_Er() {
		int er = 0;
		er = (getAbility().getTotalDex() - 8) / 2;
		return er;
	}

	public int get_Er() {
		int er = 0;
		

		int BaseEr = CalcStat.���Ÿ�ȸ��(getAbility().getTotalDex());

		er += BaseEr;
		return er;
	}

	int _add_er = 0;

	public int getAdd_Er() {
		return _add_er;
	}

	public void Add_Er(int i) {
		_add_er += i;
	}

	public int get_PlusEr() {
		int er = 0;
		er += get_Er();
		er += getAdd_Er();
		if (er < 0) {
			er = 0;
		} else {
			if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STRIKER_GALE)) {
				er = er / 3;
			}
		}

		return er;
	}

	public L1BookMark getBookMark(String name) {
		L1BookMark element = null;
		for (int i = 0; i < _bookmarks.size(); i++) {
			element = _bookmarks.get(i);
			if (element.getName().equalsIgnoreCase(name)) {
				return element;
			}
		}
		return null;
	}

	public L1BookMark getBookMark(int id) {
		L1BookMark element = null;
		for (int i = 0; i < _bookmarks.size(); i++) {
			element = _bookmarks.get(i);
			if (element.getId() == id) {
				return element;
			}
		}
		return null;
	}

	public L1BookMark getBookMark(int x, int y, int mapid) {
		L1BookMark element = null;
		for (int i = 0; i < _bookmarks.size(); i++) {
			element = _bookmarks.get(i);
			if (element.getLocX() == x && element.getLocY() == y
					&& element.getMapId() == mapid) {
				return element;
			}
		}
		return null;
	}

	public int getBookMarkSize() {
		return _bookmarks.size();
	}

	public void addBookMark(L1BookMark book) {
		_bookmarks.add(book);
	}

	public void removeBookMark(L1BookMark book) {
		_bookmarks.remove(book);
	}

	public L1BookMark[] getBookMark() {
		return (L1BookMark[]) _bookmarks.toArray(new L1BookMark[_bookmarks
				.size()]);
	}

	public L1ItemInstance getWeapon() {
		return _weapon;
	}

	public void setWeapon(L1ItemInstance weapon) {
		_weapon = weapon;
	}

	public L1ItemInstance getSecondWeapon() {
		return _secondweapon;
	}

	public void setSecondWeapon(L1ItemInstance weapon) {
		_secondweapon = weapon;
	}

	public L1ItemInstance getArmor() {
		return _armor;
	}

	public void setArmor(L1ItemInstance armor) {
		_armor = armor;
	}

	public L1Quest getQuest() {
		return _quest;
	}

	public boolean isCrown() {
		return (getClassId() == CLASSID_PRINCE || getClassId() == CLASSID_PRINCESS);
	}

	public boolean isWarrior() {
		return (getClassId() == CLASSID_WARRIOR_MALE || getClassId() == CLASSID_WARRIOR_FEMALE);
	}

	public boolean isKnight() {
		return (getClassId() == CLASSID_KNIGHT_MALE || getClassId() == CLASSID_KNIGHT_FEMALE);
	}

	public boolean isElf() {
		return (getClassId() == CLASSID_ELF_MALE || getClassId() == CLASSID_ELF_FEMALE);
	}

	public boolean isWizard() {
		return (getClassId() == CLASSID_WIZARD_MALE || getClassId() == CLASSID_WIZARD_FEMALE);
	}

	public boolean isDarkelf() {
		return (getClassId() == CLASSID_DARKELF_MALE || getClassId() == CLASSID_DARKELF_FEMALE);
	}

	public boolean isDragonknight() {
		return (getClassId() == CLASSID_DRAGONKNIGHT_MALE || getClassId() == CLASSID_DRAGONKNIGHT_FEMALE);
	}

	public boolean isIllusionist() {
		return (getClassId() == CLASSID_ILLUSIONIST_MALE || getClassId() == CLASSID_ILLUSIONIST_FEMALE);
	}

	public String getAccountName() {
		return _accountName;
	}

	public void setAccountName(String s) {
		_accountName = s;
	}

	// *********���� ***********************
	public int getBirthDay() {
		return birthday;
	}

	public void setBirthDay(int t) {
		birthday = t;
	}

	public boolean isFirstBlood() {
		return _FirstBlood;
	}

	public void setFirstBlood(boolean b) {
		_FirstBlood = b;
	}

	public int getchecktime() {
		return checktime;
	}

	public void addchecktime() {
		checktime++;
	}

	public void setchecktime(int t) {
		checktime = t;
	}

	public int getravatime() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 7200;
		return getNetConnection().getAccount().ravatime;
	}

	public void setravatime(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().ravatime = t;
	}

	public int get������õ����time() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 6000;
		return getNetConnection().getAccount().������õ����time;
	}

	public void set������õ����time(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().������õ����time = t;
	}

	public int getgirantime() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 60 * 60 * 3;
		return getNetConnection().getAccount().girantime;
	}

	public void setgirantime(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().girantime = t;
	}

	public int get�ַ�Ÿ��time() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 60 * 40;
		return getNetConnection().getAccount().�ַ�Ÿ��time;
	}

	public void set�ַ�Ÿ��time(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().�ַ�Ÿ��time = t;
	}

	public int getpc����time() {
		return ����time;
	}

	public void setpc����time(int t) {
		����time = t;
	}
	
	
	public int getpc����time() {
		return ����time;
	}

	public void setpc����time(int t) {
		����time = t;
	}

	public int getpc���time() {
		return ���time;
	}

	public void setpc���time(int t) {
		���time = t;
	}

	public int get���time() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 60 * 60 * 3;
		return getNetConnection().getAccount().���time;
	}

	public void set���time(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().���time = t;
	}
//�ؼ�


	
	public int get����time() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 60 * 30;
		return getNetConnection().getAccount().����time;
	}

	public void set����time(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().����time = t;
	}

	public int get����time() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 60 * 90;
		return getNetConnection().getAccount().����time;
	}

	public void set����time(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().����time = t;
	}

	public int get�ҷ���time() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 60 * 60;
		return getNetConnection().getAccount().�ҷ���time;
	}

	public void set�ҷ���time(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().�ҷ���time = t;
	}

	public int get�����Ѱ���time() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 60 * 60 * 2;
		return getNetConnection().getAccount().�����Ѱ���time;
	}

	public void set�����Ѱ���time(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().�����Ѱ���time = t;
	}

	public int get�����̺�Ʈtime() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 60 * 60 * 1;
		return getNetConnection().getAccount().�����̺�Ʈtime;
	}

	public void set�����̺�Ʈtime(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().�����̺�Ʈtime = t;
	}

	public int getivorytime() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 3600;
		return getNetConnection().getAccount().ivorytime;
	}

	public void setivorytime(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().ivorytime = t;
	}

	public int getivoryyaheetime() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return 3600;
		return getNetConnection().getAccount().ivoryyaheetime;
	}

	public void setivoryyaheetime(int t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().ivoryyaheetime = t;
	}

	/*
	 * public int getdctime() { if(getNetConnection() == null ||
	 * getNetConnection().getAccount() == null) return 7200; return
	 * getNetConnection().getAccount().dctime; } public void setdctime(int t) {
	 * if(getNetConnection() == null || getNetConnection().getAccount() == null)
	 * return; getNetConnection().getAccount().dctime = t; }
	 */

	public Timestamp getravaday() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().ravaday;
	}

	public void setravaday(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().ravaday = t;
	}

	public Timestamp getgiranday() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().giranday;
	}

	public void setgiranday(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().giranday = t;
	}
	
	//�Ƴ�� �̺�Ʈ ���
	private void drop() {
		if (getInventory().checkEquipped(21276)) { // ������ ������
			L1ItemInstance drop = ItemTable.getInstance().createItem(30145); // ��� ��ų ������
			for (L1ItemInstance item : getInventory().getItems()) {
				if (item.getItemId() == 21276 & item.isEquipped()) {
					sendPackets(new S_ServerMessage(3802));
					sendPackets(new S_ServerMessage(158, "$22251"));
					getInventory().removeItem(item, 1);
					L1World.getInstance().getInventory(getX(), getY(), getMapId()).storeItem(drop);
					break;
				}
			}
		}
	}
	

		private void drop��ȣ(L1ItemInstance delitem) {
			if(delitem != null){
				sendPackets(new S_ServerMessage(3802));
				sendPackets(new S_ServerMessage(158, delitem.getItem().getNameId()));
				getInventory().removeItem(delitem, 1);
				L1ItemInstance drop = ItemTable.getInstance().createItem(3000055); // ��� ��ų ������ ����� ���������̵������		
				L1World.getInstance().getInventory(getX(), getY(), getMapId()).storeItem(drop);
			}
		}

	public Timestamp get�ַ�Ÿ��day() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().�ַ�Ÿ��day;
	}

	public void set�ַ�Ÿ��day(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().�ַ�Ÿ��day = t;
	}

	public Timestamp getpc����day() {
		return ����day;
	}

	public void setpc����day(Timestamp t) {
		����day = t;
	}

	public Timestamp getpc����day() {
		return ����day;
	}

	public void setpc����day(Timestamp t) {
		����day = t;
	}

	public Timestamp getpc���day() {
		return ���day;
	}

	public void setpc���day(Timestamp t) {
		���day = t;
	}
//�ؼ�
	
	
	public Timestamp get����day() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().����day;
	}

	public void set����day(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().����day = t;
	}

	public Timestamp get���day() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().���day;
	}

	public void set���day(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().���day = t;
	}
	


	public Timestamp get����day() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().����day;
	}

	public void set����day(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().����day = t;
	}

	public Timestamp get�ҷ���day() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().�ҷ���day;
	}

	public void set�ҷ���day(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().�ҷ���day = t;
	}

	public Timestamp get������õ����day() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().������õ����day;
	}

	public void set������õ����day(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().������õ����day = t;
	}

	public Timestamp get�����Ѱ���day() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().�����Ѱ���day;
	}

	public void set�����Ѱ���day(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().�����Ѱ���day = t;
	}

	public Timestamp get�����̺�Ʈday() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().�����̺�Ʈday;
	}

	public void set�����̺�Ʈday(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().�����̺�Ʈday = t;
	}

	public Timestamp getivoryday() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().ivoryday;
	}

	public void setivoryday(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().ivoryday = t;
	}

	/*
	 * public Timestamp getdcday() { if(getNetConnection() == null ||
	 * getNetConnection().getAccount() == null) return null; return
	 * getNetConnection().getAccount().dcday; } public void setdcday(Timestamp
	 * t) { if(getNetConnection() == null || getNetConnection().getAccount() ==
	 * null) return; getNetConnection().getAccount().dcday = t; }
	 */

	public Timestamp getivoryyaheeday() {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return null;
		return getNetConnection().getAccount().ivoryyaheeday;
	}

	public void setivoryyaheeday(Timestamp t) {
		if (getNetConnection() == null
				|| getNetConnection().getAccount() == null)
			return;
		getNetConnection().getAccount().ivoryyaheeday = t;
	}

	public Timestamp getAnTime() {
		return antatime;
	}

	public void setAnTime(Timestamp t) {
		antatime = t;
	}

	public Timestamp getpaTime() {
		return papootime;
	}

	public void setpaTime(Timestamp t) {
		papootime = t;
	}

	public Timestamp getlindTime() {
		return lindtime;
	}

	public void setlindTime(Timestamp t) {
		lindtime = t;
	}

	public Timestamp getDETime() {
		return DEtime;
	}

	public void setDETime(Timestamp t) {
		DEtime = t;
	}

	public Timestamp getDETime2() {
		return DEtime2;
	}

	public void setDETime2(Timestamp t) {
		DEtime2 = t;
	}

	Timestamp PUPLEtime;

	public Timestamp getPUPLETime() {
		return PUPLEtime;
	}

	public void setPUPLETime(Timestamp t) {
		PUPLEtime = t;
	}

	Timestamp TOPAZtime;

	public Timestamp getTOPAZTime() {
		return TOPAZtime;
	}

	public void setTOPAZTime(Timestamp t) {
		TOPAZtime = t;
	}

	Timestamp TamTime;

	public Timestamp getTamTime() {
		return TamTime;
	}

	public void setTamTime(Timestamp t) {
		TamTime = t;
	}

	private int bookmark_max;

	public int getBookmarkMax() {
		return bookmark_max;
	}

	public void setBookmarkMax(int t) {
		bookmark_max = t;
	}

	public int getBaseMaxHp() {
		return _baseMaxHp;
	}

	public void addBaseMaxHp(int i) {
		i += _baseMaxHp;
		if (i >= 32767) {
			i = 32767;
		} else if (i < 1) {
			i = 1;
		}
		addMaxHp(i - _baseMaxHp);
		_baseMaxHp = i;
	}

	public int getBaseMaxMp() {
		return _baseMaxMp;
	}

	public void addBaseMaxMp(int i) {
		i += _baseMaxMp;
		if (i >= 32767) {
			i = 32767;
		} else if (i < 0) {
			i = 0;
		}
		addMaxMp(i - _baseMaxMp);
		_baseMaxMp = i;
	}

	public int getBaseAc() {
		return _baseAc;
	}

	public int getBaseDmgup() {
		return _baseDmgup;
	}

	public int getBaseBowDmgup() {
		return _baseBowDmgup;
	}

	public int getBaseHitup() {
		return _baseHitup;
	}

	public int getBaseBowHitup() {
		return _baseBowHitup;
	}

	public void setBaseMagicHitUp(int i) {
		_baseMagicHitup = i;
	}

	public int getBaseMagicHitUp() {
		return _baseMagicHitup;
	}

	public void setBaseMagicCritical(int i) {
		_baseMagicCritical = i;
	}

	public int getBaseMagicCritical() {
		return _baseMagicCritical;
	}

	public void setBaseMagicDmg(int i) {
		_baseMagicDmg = i;
	}

	public int getBaseMagicDmg() {
		return _baseMagicDmg;
	}

	public void setBaseMagicDecreaseMp(int i) {
		_baseMagicDecreaseMp = i;
	}

	public int getBaseMagicDecreaseMp() {
		return _baseMagicDecreaseMp;
	}

	public int getAdvenHp() {
		return _advenHp;
	}

	public void setAdvenHp(int i) {
		_advenHp = i;
	}

	public int getAdvenMp() {
		return _advenMp;
	}

	public void setAdvenMp(int i) {
		_advenMp = i;
	}

	public int getHighLevel() {
		return _highLevel;
	}

	public void setHighLevel(int i) {
		_highLevel = i;
	}

	public int getElfAttr() {
		return _elfAttr;
	}

	public void setElfAttr(int i) {
		_elfAttr = i;
	}

	public int getExpRes() {
		return _expRes;
	}

	public void setExpRes(int i) {
		_expRes = i;
	}

	public int getPartnerId() {
		return _partnerId;
	}

	public void setPartnerId(int i) {
		_partnerId = i;
	}

	public int getOnlineStatus() {
		return _onlineStatus;
	}

	public void setOnlineStatus(int i) {
		_onlineStatus = i;
	}

	public int getHomeTownId() {
		return _homeTownId;
	}

	public void setHomeTownId(int i) {
		_homeTownId = i;
	}

	public int getContribution() {
		return _contribution;
	}

	public void setContribution(int i) {
		_contribution = i;
	}

	public int getHellTime() {
		return _hellTime;
	}

	public void setHellTime(int i) {
		_hellTime = i;
	}

	public boolean isBanned() {
		return _banned;
	}

	public void setBanned(boolean flag) {
		_banned = flag;
	}

	public int get_food() {
		return _food;
	}

	public void set_food(int i) {
		_food = i;
	}

	public L1EquipmentSlot getEquipSlot() {
		return _equipSlot;
	}

	/** ������Ÿ�Դϴ�. **/
	public int getBapodmg() {
		return _bapodmg;
	}

	public void setBapodmg(int i) {
		_bapodmg = (byte) i;
	}

	/** ������Ÿ�Դϴ�. **/

	/** ���������Դϴ�. **/
	public int getNBapoLevel() {
		return _nbapoLevel;
	}

	public void setNBapoLevell(int i) {
		_nbapoLevel = (byte) i;
	}

	public int getOBapoLevel() {
		return _obapoLevel;
	}

	public void setOBapoLevell(int i) {
		_obapoLevel = (byte) i;
	}

	/** ���������Դϴ�. **/

	public static ArrayList<String> getPCs(String accname) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			result = CharacterTable.getInstance().AccountCharName(accname);
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		return result;
	}

	public static L1PcInstance load(String charName) {
		L1PcInstance result = null;
		try {
			result = CharacterTable.getInstance().loadCharacter(charName);
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		return result;
	}

	public void save() throws Exception {
		if (this instanceof L1RobotInstance) {
			return;
		}
		// if (isGhost()) {
		// return;
		// }
		CharacterTable.getInstance().storeCharacter(this);
	}

	public void saveInventory() {
		for (L1ItemInstance item : getInventory().getItems()) {
			getInventory().saveItem(item, item.getRecordingColumns());
		}
	}

	public void setRegenState(int state) {
		setHpRegenState(state);
		setMpRegenState(state);
	}

	public void setHpRegenState(int state) {
		if (_HpcurPoint < state)
			return;

		this._HpcurPoint = state;
		// _mpRegen.setState(state);
		// _hpRegen.setState(state);
	}
	
	private void huntoptionminus(L1PcInstance pc) { //����ɼ�
		if(pc.getHuntCount() == 1){
			if(pc.isWizard() || pc.isIllusionist()){
				if(pc.getHuntPrice() == 1000000){
					pc.getAbility().addSp(-1);
					pc.sendPackets(new S_SPMR(pc));
				} 
			} else if (pc.isCrown() || pc.isKnight() || pc.isDarkelf() || pc.isDragonknight() || pc.isElf() || pc.isWarrior()){
				if(pc.getHuntPrice() == 1000000){
					pc.addDmgup(-1);
					pc.addBowDmgup(-1);
					pc.addDamageReductionByArmor(-1);
				} 
			}
		} 
			if(pc.getHuntCount() == 2){
				if(pc.isWizard() || pc.isIllusionist()){
					if(pc.getHuntPrice() == 5000000){
						pc.getAbility().addSp(-2);
						pc.sendPackets(new S_SPMR(pc));
					} 
				} else if (pc.isCrown() || pc.isKnight() || pc.isDarkelf() || pc.isDragonknight() || pc.isElf() || pc.isWarrior()){
					if(pc.getHuntPrice() == 5000000){
						pc.addDmgup(-2);
						pc.addBowDmgup(-2);
						pc.addDamageReductionByArmor(-2);
					} 
				}
			} 
				if(pc.getHuntCount() == 3){
					if(pc.isWizard() || pc.isIllusionist()){
						if(pc.getHuntPrice() == 10000000){
							pc.getAbility().addSp(-3);
							pc.sendPackets(new S_SPMR(pc));
						} 
					} else if (pc.isCrown() || pc.isKnight() || pc.isDarkelf() || pc.isDragonknight() || pc.isElf() || pc.isWarrior()){
						if(pc.getHuntPrice() == 10000000){
							pc.addDmgup(-3);
							pc.addBowDmgup(-3);
							pc.addDamageReductionByArmor(-3);
						} 
					}
				} 
	}

	public void setMpRegenState(int state) {
		if (_MpcurPoint < state)
			return;

		this._MpcurPoint = state;
		// _mpRegen.setState(state);
		// _hpRegen.setState(state);
	}

	public int getMaxWeight() {
		int str = getAbility().getTotalStr();
		int con = getAbility().getTotalCon();
		int maxWeight = CalcStat.getMaxWeight(str, con);
		double plusWeight = getWeightReduction();

		maxWeight += plusWeight;

		int dollWeight = 0;
		for (L1DollInstance doll : getDollList()) {
			dollWeight = doll.getWeightReductionByDoll();
		}

		maxWeight += dollWeight;
		int magicWeight = 0;

		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DECREASE_WEIGHT)) {
			magicWeight = 180;
		}

		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.JOY_OF_PAIN)) {
			magicWeight = 480;
		}

		maxWeight += magicWeight;
		maxWeight *= Config.RATE_WEIGHT_LIMIT;

		return maxWeight;
	}

	public boolean isUgdraFruit() {
		return getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_FRUIT);
	}

	public boolean isFastMovable() {
		return (isSGm()
				|| isGm()
				|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.HOLY_WALK)
				|| getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.MOVING_ACCELERATION) || getSkillEffectTimerSet()
				.hasSkillEffect(L1SkillId.WIND_WALK));
	}

	public boolean isBloodLust() {
		return getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BLOOD_LUST);
	}

	public boolean isBrave() {
		return (isSGm() || isGm() || getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_BRAVE));
	}

	public boolean isElfBrave() {
		return getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_ELFBRAVE);
	}

	public boolean isHaste() {
		return (isSGm()
				|| isGm()
				|| getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.STATUS_HASTE)
				|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.HASTE)
				|| getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.GREATER_HASTE) || getMoveState()
				.getMoveSpeed() == 1);
	}

	public boolean isInvisDelay() {
		return (invisDelayCounter > 0);
	}

	public void addInvisDelayCounter(int counter) {
		synchronized (_invisTimerMonitor) {
		invisDelayCounter += counter;
		}
	}

	public void beginInvisTimer() {
		final long DELAY_INVIS = 500L;
		addInvisDelayCounter(1);
		GeneralThreadPool.getInstance().pcSchedule(new L1PcInvisDelay(getId()),
				DELAY_INVIS);
	}

	public synchronized void addExp(int exp) {
		_exp += exp;
		if (_exp > ExpTable.MAX_EXP) {
			_exp = ExpTable.MAX_EXP;
		}
	}

	public synchronized void addContribution(int contribution) {
		_contribution += contribution;
	}

	public void beginExpMonitor() {
		// final long INTERVAL_EXP_MONITOR = 500;
		// _expMonitorFuture = GeneralThreadPool.getInstance()
		// .pcScheduleAtFixedRate(new L1PcExpMonitor(getId()), 0L,
		// INTERVAL_EXP_MONITOR);
		GeneralThreadPool.getInstance().pcSchedule(new L1PcExpMonitor(this), 1);
	}

	// private static final int ������[] =
	// {21099,21100,21101,21102,21103,21104,21105,21106,
	// 21107,21108,21109,21110,21111,21112,21254,
	// 7,35,48,73,105,120,147,156,174,175,224,7232};
	//
	private void levelUp(int gap) {
		byte old = (byte) getLevel();
		resetLevel();

		if (getLevel() == 99 && Config.ALT_REVIVAL_POTION) {
			try {
				L1Item l1item = ItemTable.getInstance().getTemplate(43000);
				if (l1item != null) {
					getInventory().storeItem(43000, 1);
					sendPackets(new S_ServerMessage(403, l1item.getName()),
							true);
				} else {
					sendPackets(new S_SystemMessage("ȯ���� ���� �Լ��� �����߽��ϴ�."), true);
				}
			} catch (Exception e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				sendPackets(new S_SystemMessage("ȯ���� ���� �Լ��� �����߽��ϴ�."), true);
			}
		}
		// ��ȣ���� �ڵ�Ż��
		String BloodName = getClanname();
		if (getLevel() >= Config.AUTO_REMOVECLAN && BloodName.equalsIgnoreCase("�űԹ���")) {
			try {
				L1Clan clan = L1World.getInstance().getClan("�űԹ���");
				L1PcInstance clanMember[] = clan.getOnlineClanMember();
				String player_name = getName();
				String clan_name = getClanname();

				for (int i = 0; i < clanMember.length; i++) {
					clanMember[i].sendPackets(new S_ServerMessage(ServerMessage.LEAVE_CLAN, player_name, clan_name)); // \f1%0�� %1������ Ż���߽��ϴ�.
				}
				ClearPlayerClanData(clan);
				clan.removeClanMember(player_name);
				save();
				saveInventory();
				L1Teleport.teleport(this, 33443, 32797, (short) 4, 5,
						true); // / ���Ե� ���� (������������������)
			} catch (Exception e) {
			}
		}
		for (int i = 0; i < gap; i++) {
			int randomHp = 0;
			int randomMp = 0;
			if (old++ <= 51) {
				randomHp = CalcStat.��������(getType(), getBaseMaxHp(),
						getAbility().getTotalCon());
				randomMp = CalcStat.����������(getType(), getBaseMaxMp(),
						getAbility().getTotalWis());
			} else {
				randomHp = CalcStat.��������(getType(), getBaseMaxHp(),
						getAbility().getTotalCon());
				randomMp = CalcStat.����������(getType(), getBaseMaxMp(),
						getAbility().getTotalWis());
			}
			addBaseMaxHp(randomHp);
			addBaseMaxMp(randomMp);
		}

		this.setCurrentHp(getMaxHp());
		this.setCurrentMp(getMaxMp());
		resetBaseAc();
		resetBaseMr();
		if (getLevel() > getHighLevel() && getReturnStat() == 0) {
			setHighLevel(getLevel());
		}

		try {
			save();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}

		//���� ���� ����
		L1Quest quest = getQuest();
		L1ItemInstance item = null;
		int lv52_step = quest.get_step(L1Quest.QUEST_SUB_LEVEL52);
		if (getLevel() >= 82 && lv52_step != L1Quest.QUEST_END) {
			item = getInventory(). storeItem(888820, 1); //���� ���� ����
			getQuest().set_end(L1Quest.QUEST_SUB_LEVEL52);; 
			sendPackets(new S_ServerMessage(403, item.getName()));
		}
		if (getLevel() >= 51
				&& getLevel() - 50 > getAbility().getBonusAbility()
				&& getAbility().getAmount() < 210) {
			int temp = (getLevel() - 50) - getAbility().getBonusAbility();
			S_bonusstats bs = new S_bonusstats(getId(), temp);
			sendPackets(bs, true);
		}

		if (getLevel() >= 45) {
			if (getMapId() == 69 || getMapId() == 68 || getMapId() == 2005) {
				L1Teleport.teleport(this, 33082, 33389, (short) 4, 5, true);
			} // else if (getMapId() == 68) {
				// L1Teleport.teleport(this, 32574, 32941, (short) 0, 5, true);
				// }
		}
		if (getLevel() >= 60) { // Ŭ����� ���� 60������ ���� �ڵ���
            if (getMapId() == 7783 || getMapId() == 12146 || getMapId() == 12149 || getMapId() == 12147
                    || getMapId() == 12148) {
                int[] loc = L1TownLocation.getGetBackLoc(L1TownLocation.TOWNID_GIRAN);
                L1Teleport.teleport(this, loc[0], loc[1], (short) loc[2], this.getHeading(), true);

            }
		}
		
		if (GMCommands.�ɸ��������ڹ� && getLevel() == 50) {
			boolean ck = IpPhoneCertificationTable.getInstance().list()
					.contains(getNetConnection().getIp());
			if (!ck)
				L1Teleport.teleport(this, 32736, 32796, (short) 99, 5, true);
		}

		if (getLevel() >= 52) { // ���� ����
			if (getMapId() == 2010 || getMapId() == 2233 || getMapId() == 2234
					|| getMapId() == 2235) {// ��ǳ���ô���
				L1Teleport.teleport(this, 33080, 33390, (short) 4, 5, true); // WB
			}
		}
		
		if (getLevel() >= 83) { // ���� ����
			if (getMapId() == 1 || getMapId() == 2) {// ����
				L1Teleport.teleport(this, 33080, 33390, (short) 4, 5, true); // WB
			}
		}
		if (getLevel() >= 70) { // ���� ����
			if (getMapId() == 777) { // �������� ������� ��(�׸����� ����)
				L1Teleport.teleport(this, 34043, 32184, (short) 4, 5, true); // �����
																				// ž��
			} else if (getMapId() == 778 || getMapId() == 779) { // �������� �������
																	// ��(����� ����)
				L1Teleport.teleport(this, 32608, 33178, (short) 4, 5, true); // WB
			}
		}
		if (getLevel() >= 99) { // ���� ����
			if ((getMapId() >= 25 && getMapId() <= 28)
					|| (getMapId() >= 2221 && getMapId() <= 2232)) {// ���ô���
				L1Teleport.teleport(this, 33080, 33390, (short) 4, 5, true); // WB
			}
		}

		if (getLevel() >= 52) {
			sendPackets(new S_SkillSound(getId(), 8688), true);
		}
		if (getLevel() >= 60) {
			if (���͹���) {
				sendPackets(new S_PacketBox(S_PacketBox.���͹���, 0), true);
				���͹��� = false;
			}
		}
		if (getLevel() >= Config.MAX_����_DUNGEON_LEVEL) {
			if (getMapId() >= 30 && getMapId() <= 37)
				L1Teleport.teleport(this, 33444, 32798, (short) 4, 5, true);
		}
		if (getLevel() >= Config.MAX_�Ŀ���_DUNGEON_LEVEL) {
			if (getMapId() == 814) {
				L1Teleport.teleport(this, 33611, 33243, (short) 4, 5, true);
			}
		}
		CheckStatus();
		sendPackets(new S_OwnCharStatus(this), true);
		sendPackets(new S_PacketBox(S_PacketBox.char_ER, get_PlusEr()), true);
		// sendPackets(new S_OwnCharPack(this), true);
		// Broadcaster.broadcastPacket(this, new S_OtherCharPacks(this), true);
		sendPackets(new S_SPMR(this), true);
	}

	private boolean fdcheck() {
		if (this.getNetConnection() == null)
			return false;
		if (this.getNetConnection().isClosed())
			return false;
		if (!(getMapId() >= 2600 && getMapId() <= 2699))
			return false;

		return true;
	}

	public int �����ڴ��ð� = 10;
	
	public �ڽ����� _�� = null;

	public void �δ�������() {// int x, int y, int m, int curm
		if (_�� != null) {
			_��.����();
		}
		_�� = new �ڽ�����(this);
		GeneralThreadPool.getInstance().execute(_��);// x, y, m, curm, this
	}

	class �ڽ����� implements Runnable {
		L1PcInstance _pc = null;
		boolean ck = true;
		int time = 1800;
		L1ItemInstance item = null;

		public void settime(int t) {
			time = t;
		}

		public void ����() {
			ck = false;
		}

		public �ڽ�����(L1PcInstance pc) {
			_pc = pc;
		}

		/*
		 * ��������Ʈ�� ������ �����ϴ�.(�����ð�: 30��)18648 ��������Ʈ�� ������ �������ϴ�.(�����ð�: 20��)18649
		 * ���� �� �ż��� ��Ÿ �����ϴ�.(�����ð�: 10��)18650 ���� ���� ������ �����մϴ�.(�����ð�: 5��)18651 �ð���
		 * �� �ȳ������� �����ϴ�.(�����ð�: 3��)18652 �ǽ��� ��������ϴ�.(�����ð�: 1��)18653
		 */
		@Override
		public void run() {
			try {
				while (ck) {
					if (!fdcheck()) {
						ck = false;
						return;
					}
					if (time == 1800) {
						_pc.sendPackets(new S_PacketBox(
								S_PacketBox.GREEN_MESSAGE, "$18648"));
					} else if (time == 1200) {
						_pc.sendPackets(new S_PacketBox(
								S_PacketBox.GREEN_MESSAGE, "$18649"));
					} else if (time == 600) {
						_pc.sendPackets(new S_PacketBox(
								S_PacketBox.GREEN_MESSAGE, "$18650"));
					} else if (time == 300) {
						_pc.sendPackets(new S_PacketBox(
								S_PacketBox.GREEN_MESSAGE, "$18651"));
					} else if (time == 180) {
						_pc.sendPackets(new S_PacketBox(
								S_PacketBox.GREEN_MESSAGE, "$18652"));
					} else if (time == 60) {
						_pc.sendPackets(new S_PacketBox(
								S_PacketBox.GREEN_MESSAGE, "$18652"));
					}

					if (time < 5) {
						_pc.sendPackets(new S_ServerMessage(1484 - time));
					} else if (time == 10) {
						_pc.sendPackets(new S_ServerMessage(1478));
					} else if (time == 20) {
						_pc.sendPackets(new S_ServerMessage(1477));
					} else if (time == 30) {
						_pc.sendPackets(new S_ServerMessage(1476));
					}
					if (time <= 0) {
						if (_pc.getInventory().checkItem(7236)) {
							item = _pc.getInventory().checkEquippedItem(7236);
							if (item != null) {
								_pc.getInventory().setEquipped(item, false,
										false, false);
							}
							_pc.getInventory().consumeItem(7236, 1);
						}
						_pc.setTelType(5);
						_pc.sendPackets(new S_SabuTell(_pc), true);
						ck = false;
						return;
					}
					time--;
					Thread.sleep(1000);
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void levelDown(int gap) {
		byte old = (byte) getLevel();
		resetLevel();
		if (getLevel() >= 52) {
			sendPackets(new S_SkillSound(getId(), 8688), true);
		}
		for (int i = 0; i > gap; i--) {
			int randomHp = 0;
			int randomMp = 0;
			if (old-- <= 51) {
				randomHp = CalcStat.��������(getType(), 0, getAbility()
						.getTotalCon());
				randomMp = CalcStat.����������(getType(), 0, getAbility()
						.getTotalWis());
			} else {
				randomHp = CalcStat.��������(getType(), 0, getAbility()
						.getTotalCon());
				randomMp = CalcStat.����������(getType(), 0, getAbility()
						.getTotalWis());
			}
			addBaseMaxHp((short) -randomHp);
			addBaseMaxMp((short) -randomMp);
		}
		// resetBaseHitup();
		// resetBaseDmgup();
		resetBaseAc();
		resetBaseMr();
		if (!isGm() && Config.LEVEL_DOWN_RANGE != 0) {
			if (getHighLevel() - getLevel() >= Config.LEVEL_DOWN_RANGE) {
				// Account.ban(getAccountName());
				sendPackets(new S_ServerMessage(64), true);
				sendPackets(new S_Disconnect(), true);
				if (getNetConnection() != null)
					getNetConnection().close();
				_log.info(String.format("[%s]: ���� ������ �Ѿ��� ������ ���� ���� �߽��ϴ�.",
						getName()));
			}
		}

		/*
		 * if (getLevel() >= 70 && getLevel() < 75) { initLevelBonus();
		 * setLevelBonus(lvl70); addLevelBonus(); } else if (getLevel() >= 75 &&
		 * getLevel() < 79) { initLevelBonus(); setLevelBonus(lvl75);
		 * addLevelBonus(); } else if (getLevel() >= 80 && getLevel() < 84) {
		 * initLevelBonus(); setLevelBonus(lvl80); addLevelBonus(); } else if
		 * (getLevel() >= 85 && getLevel() < 90) { initLevelBonus();
		 * setLevelBonus(lvl85); addLevelBonus(); } else if (getLevel() >= 90 &&
		 * getLevel() < 95) { initLevelBonus(); setLevelBonus(lvl90);
		 * addLevelBonus(); } else if (getLevel() >= 95) { initLevelBonus();
		 * setLevelBonus(lvl95); addLevelBonus(); }
		 */

		try {
			save();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		sendPackets(new S_OwnCharStatus(this), true);
		sendPackets(new S_PacketBox(S_PacketBox.char_ER, get_PlusEr()), true);
	}

	public void beginGameTimeCarrier() {
		new GameTimeCarrier(this).start();
	}

	public boolean isGhost() {
		return _ghost;
	}

	private void setGhost(boolean flag) {
		_ghost = flag;
	}

	public boolean isReserveGhost() {
		return _isReserveGhost;
	}

	private void setReserveGhost(boolean flag) {
		_isReserveGhost = flag;
	}

	public void beginGhost(int locx, int locy, short mapid, boolean canTalk) {
		beginGhost(locx, locy, mapid, canTalk, 0);
	}

	public void beginGhost(int locx, int locy, short mapid, boolean canTalk,
			int sec) {
		if (isGhost()) {
			return;
		}
		setGhost(true);
		_ghostSaveLocX = getX();
		_ghostSaveLocY = getY();
		_ghostSaveMapId = getMapId();
		_ghostSaveHeading = getMoveState().getHeading();
		L1Teleport.teleport(this, locx, locy, mapid, 5, true);
		if (sec > 0) {
			_ghostFuture = GeneralThreadPool.getInstance().pcSchedule(
					new L1PcGhostMonitor(getId()), sec * 1000);
		}
	}

	public void makeReadyEndGhost() {
		setReserveGhost(true);
		L1Teleport.teleport(this, _ghostSaveLocX, _ghostSaveLocY,
				_ghostSaveMapId, _ghostSaveHeading, true);
	}

	public void DeathMatchEndGhost() {
		endGhost();
		L1Teleport.teleport(this, 32614, 32735, (short) 4, 5, true);
	}

	public void endGhost() {
		setGhost(false);
		setReserveGhost(false);
		if (_ghostFuture != null) {
			_ghostFuture.cancel(true);
			_ghostFuture = null;
		}
	}

	public void beginHell(boolean isFirst) {
		if (getMapId() != 666) {
			int locx = 32701;
			int locy = 32777;
			short mapid = 666;
			L1Teleport.teleport(this, locx, locy, mapid, 5, false);
		}

		if (isFirst) {
			if (get_PKcount() <= 10) {
				setHellTime(180);
			} else {
				setHellTime(300 * (get_PKcount() - 100) + 300);
			}
			sendPackets(new S_BlueMessage(552, String.valueOf(get_PKcount()),
					String.valueOf(getHellTime() / 60)));
		} else {
			sendPackets(new S_BlueMessage(637, String.valueOf(getHellTime())));
		}
		if (_hellFuture == null) {
			_hellFuture = GeneralThreadPool.getInstance()
					.pcScheduleAtFixedRate(new L1PcHellMonitor(getId()), 0L,
							1000L);
		}
	}

	public void endHell() {
		if (_hellFuture != null) {
			_hellFuture.cancel(false);
			_hellFuture = null;
		}
		int[] loc = L1TownLocation
				.getGetBackLoc(L1TownLocation.TOWNID_ORCISH_FOREST);
		L1Teleport.teleport(this, loc[0], loc[1], (short) loc[2], 5, true);
		try {
			save();
		} catch (Exception ignore) {
		}
	}

	@Override
	public void setPoisonEffect(int effectId) {
		S_Poison po = new S_Poison(getId(), effectId);
		sendPackets(po);
		if (!isGmInvis() && !isGhost() && !isInvisble()) {
			Broadcaster.broadcastPacket(this, po);
		}
	}

	@Override
	public void healHp(int pt) {
		super.healHp(pt);
		sendPackets(new S_HPUpdate(this), true);
	}

	@Override
	public int getKarma() {
		return _karma.get();
	}

	@Override
	public void setKarma(int i) {
		_karma.set(i);
	}

	public void addKarma(int i) {
		synchronized (_karma) {
			_karma.add(i);
			sendPackets(new S_PacketBox(this, S_PacketBox.KARMA), true);
		}
	}

	public int getKarmaLevel() {
		return _karma.getLevel();
	}

	public int getKarmaPercent() {
		return _karma.getPercent();
	}

	public Timestamp getLastPk() {
		return _lastPk;
	}

	public void setLastPk(Timestamp time) {
		_lastPk = time;
	}

	public void setLastPk() {
		_lastPk = new Timestamp(System.currentTimeMillis());
	}

	public boolean isWanted() {
		if (_lastPk == null) {
			return false;
		} else if (System.currentTimeMillis() - _lastPk.getTime() > 24 * 3600 * 1000) {
			setLastPk(null);
			return false;
		}
		return true;
	}

	public Timestamp getDeleteTime() {
		return _deleteTime;
	}

	public void setDeleteTime(Timestamp time) {
		_deleteTime = time;
	}

	public int getWeightReduction() {
		return _weightReduction;
	}

	public void addWeightReduction(int i) {
		_weightReduction += i;
	}

	public int getHasteItemEquipped() {
		return _hasteItemEquipped;
	}

	public void addHasteItemEquipped(int i) {
		_hasteItemEquipped += i;
	}

	private boolean _��ƾ���� = false;

	public boolean is��ƾ����() {
		return _��ƾ����;
	}

	public void set��ƾ(boolean f) {
		_��ƾ���� = f;
	}

	public void removeHasteSkillEffect() {
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SLOW))
			getSkillEffectTimerSet().removeSkillEffect(L1SkillId.SLOW);
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.MASS_SLOW))
			getSkillEffectTimerSet().removeSkillEffect(L1SkillId.MASS_SLOW);
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ENTANGLE))
			getSkillEffectTimerSet().removeSkillEffect(L1SkillId.ENTANGLE);
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.HASTE))
			getSkillEffectTimerSet().removeSkillEffect(L1SkillId.HASTE);
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.GREATER_HASTE))
			getSkillEffectTimerSet().removeSkillEffect(L1SkillId.GREATER_HASTE);
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_HASTE))
			getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_HASTE);
	}

	/*
	 * public void resetBaseDmgup() { int newBaseDmgup = 0; int newBaseBowDmgup
	 * = 0; int newBaseStatDmgup = CalcStat.�ٰŸ������(getAbility().getTotalStr());
	 * int newBaseStatBowDmgup = CalcStat.���Ÿ������(getAbility().getTotalDex()); if
	 * (isWarrior() || isKnight() || isDarkelf() || isDragonknight()) {
	 * newBaseDmgup = getLevel() / 10; newBaseBowDmgup = 0;
	 * 
	 * } else if (isElf()) { newBaseDmgup = 0; newBaseBowDmgup = getLevel() /
	 * 10; } addDmgup(newBaseStatDmgup); addBowDmgup(newBaseStatBowDmgup);
	 * _baseDmgup = newBaseStatDmgup; _baseBowDmgup = newBaseStatBowDmgup; }
	 */

	/*
	 * public void resetBaseHitup() { int newBaseStatHitup =
	 * CalcStat.�ٰŸ�����(getAbility().getTotalStr()); int newBaseStatBowHitup =
	 * CalcStat.���Ÿ�����(getAbility().getTotalDex());
	 * 
	 * if (isCrown()) { newBaseHitup = getLevel() / 5; newBaseBowHitup =
	 * getLevel() / 5; } else if (isKnight()||isWarrior()) { newBaseHitup =
	 * getLevel() / 3; newBaseBowHitup = getLevel() / 3; } else if (isElf()) {
	 * newBaseHitup = getLevel() / 5; newBaseBowHitup = getLevel() / 5; } else
	 * if (isDarkelf()) { newBaseHitup = getLevel() / 3; newBaseBowHitup =
	 * getLevel() / 3; } else if (isDragonknight()) { newBaseHitup = getLevel()
	 * / 3; newBaseBowHitup = getLevel() / 3; } else if (isIllusionist()) {
	 * newBaseHitup = getLevel() / 5; newBaseBowHitup = getLevel() / 5; }else if
	 * (isWizard()) { newBaseHitup = getLevel() / 5; }
	 * addHitup(newBaseStatHitup); addBowHitup(newBaseStatBowHitup); _baseHitup
	 * = newBaseStatHitup; _baseBowHitup = newBaseStatBowHitup; }
	 */

	public void resetBaseAc() {
		int newAc = CalcStat.��������(getAbility().getTotalDex());
		// int newbaseAc = CalcStat.calcBaseAc(getType()
		// ,getAbility().getBaseDex());
		// *getAC().setAc(newAc);
		_baseAc = newAc; // + newbaseAc;
		getAC().wisacreset(newAc);
		sendPackets(new S_OwnCharAttrDef(this));
	}

	public void resetBaseMr() {
		// int newBaseMr = CalcStat.calcBaseMr(getType(),
		// getAbility().getBaseWis());
		int newMr = 0;

		/*
		 * if (isCrown()) newMr = 10; else if (isElf()) newMr = 25; else if
		 * (isWizard()) newMr = 15; else if (isDarkelf()) newMr = 10; else if
		 * (isDragonknight()) newMr = 18; else if (isIllusionist()) newMr = 20;
		 */
		newMr += CalcStat.�������(getType(), getAbility().getTotalWis());
		newMr += getLevel() / 2;

		resistance.setBaseMr(newMr);
	}

	public void resetLevel() {
		//int sb = ExpTable.getLevelByExp(_exp);
		//eva.LogServerAppend(getName() + " �� ��üũ", String.valueOf(sb) );
		//System.out.println("������ : "+ExpTable.getLevelByExp(_exp));
		setLevel(ExpTable.getLevelByExp(_exp));
		updateLevel();
	}

	private static final int lvlTable[] = new int[] { 30, 25, 20, 16, 14, 12,
			11, 10, 9, 3, 2 };

	public void updateLevel() {

		int regenLvl = Math.min(10, getLevel());
		if (30 <= getLevel() && isKnight()) {
			regenLvl = 11;
		}

		synchronized (this) {
			setHpregenMax(lvlTable[regenLvl - 1] * REGENSTATE_NONE);
		}

	}

	public void refresh() {
		CheckChangeExp();
		resetLevel();
		// resetBaseHitup();
		// resetBaseDmgup();
		resetBaseMr();
		resetBaseAc();
		// addPotionPlus(CalcStat.����ȸ������(getAbility().getTotalCon()));
		// setBaseMagicDecreaseMp(CalcStat.���Ҹ𰨼�(getAbility().getTotalInt()));
		setBaseMagicHitUp(CalcStat.��������(getAbility().getTotalInt()));
		// setBaseMagicCritical(CalcStat.����ġ��Ÿ(getAbility().getTotalInt()));
		// setBaseMagicDmg(CalcStat.���������(getAbility().getTotalInt()));
	}

	public void checkChatInterval() {
		long nowChatTimeInMillis = System.currentTimeMillis();
		if (_chatCount == 0) {
			_chatCount++;
			_oldChatTimeInMillis = nowChatTimeInMillis;
			return;
		}

		long chatInterval = nowChatTimeInMillis - _oldChatTimeInMillis;
		if (chatInterval > 2000) {
			_chatCount = 0;
			_oldChatTimeInMillis = 0;
		} else {
			if (_chatCount >= 3) {
				getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.STATUS_CHAT_PROHIBITED, 120 * 1000);
				S_SkillIconGFX si = new S_SkillIconGFX(36, 120);
				sendPackets(si, true);
				S_ServerMessage sm = new S_ServerMessage(153);
				sendPackets(sm, true);
				_chatCount = 0;
				_oldChatTimeInMillis = 0;
			}
			_chatCount++;
		}
	}

	/** �׺� ���� (by. ����) **/
	public boolean Gamble_Somak = false;
	public String Gamble_Text = "";

	
	private long _telldelayTime = 0;
	public long gettelldelayTime() { return _telldelayTime; }	
	public void settelldelayTime(long l) { _telldelayTime = l ; } 
	/** �Ҹ� �� �ֻ��� ���� (������) */
	private boolean _isGambling = false;

	public boolean isGambling() {
		return _isGambling;
	}

	public void setGambling(boolean flag) {
		_isGambling = flag;
	}

	private int _gamblingmoney = 0;

	public int getGamblingMoney() {
		return _gamblingmoney;
	}

	public void setGamblingMoney(int i) {
		_gamblingmoney = i;
	}

	// //##########�׺� �Ҹ� �ֻ��� �����############
	private boolean _isGambling1 = false;

	public boolean isGambling1() {
		return _isGambling1;
	}

	public void setGambling1(boolean flag) {
		_isGambling1 = flag;
	}

	private int _gamblingmoney1 = 0;

	public int getGamblingMoney1() {
		return _gamblingmoney1;
	}

	public void setGamblingMoney1(int i) {
		_gamblingmoney1 = i;
	}

	// //##########�׺� �Ҹ� �ֻ��� �����############
	private boolean _isGambling3 = false;

	public boolean isGambling3() {
		return _isGambling3;
	}

	public void setGambling3(boolean flag) {
		_isGambling3 = flag;
	}

	private int _gamblingmoney3 = 0;

	public int getGamblingMoney3() {
		return _gamblingmoney3;
	}

	public void setGamblingMoney3(int i) {
		_gamblingmoney3 = i;
	}

	// //##########�׺� �Ҹ� �ֻ��� �����############
	private boolean _isGambling4 = false;

	public boolean isGambling4() {
		return _isGambling4;
	}

	public void setGambling4(boolean flag) {
		_isGambling4 = flag;
	}

	private int _gamblingmoney4 = 0;

	public int getGamblingMoney4() {
		return _gamblingmoney4;
	}

	public void setGamblingMoney4(int i) {
		_gamblingmoney4 = i;
	}
	/** �Ҹ� �� �ֻ��� �� �� �� �� ���� */

	public void CheckChangeExp() {
		int level = ExpTable.getLevelByExp(getExp());
		int char_level = CharacterTable.getInstance().PcLevelInDB(getId());
		if (char_level == 0) { // 0�̶��..��������?
			return; // �׷� �׳� ����
		}
		int gap = level - char_level;
		if (gap == 0) {
			sendPackets(new S_OwnCharStatus(this));
			sendPackets(new S_PacketBox(S_PacketBox.char_ER, get_PlusEr()),
					true);
			int percent = ExpTable.getExpPercentage(char_level, getExp());
			if (char_level >= 60 && char_level <= 64) {
				if (percent >= 10)
					getSkillEffectTimerSet()
							.removeSkillEffect(L1SkillId.���������ʽ�);
			} else if (char_level >= 65) {
				if (percent >= 5) {
					getSkillEffectTimerSet()
							.removeSkillEffect(L1SkillId.���������ʽ�);
				}
			}
			return;
		}

		// ������ ��ȭ���� ���
		if (gap > 0) {
			levelUp(gap);
			if (getLevel() >= 60) {
				getSkillEffectTimerSet().setSkillEffect(L1SkillId.���������ʽ�,
						10800000);
				sendPackets(new S_PacketBox(10800, true, true), true);
			}
		} else if (gap < 0) {
			levelDown(gap);
			getSkillEffectTimerSet().removeSkillEffect(L1SkillId.���������ʽ�);
		}
	}

	public void CheckStatus() {
		if (!getAbility().isNormalAbility(getClassId(), getLevel(),
				getHighLevel(), getAbility().getBaseAmount())
				&& !isGm()) {
			SpecialEventHandler.getInstance().ReturnStats(this);
		}
	}

	private int _returnstatus;

	public synchronized int getReturnStatus() {
		return _returnstatus;
	}

	public synchronized void setReturnStatus(int i) {
		_returnstatus = i;
	}

	private L1StatReset _statReset;

	public void setStatReset(L1StatReset sr) {
		_statReset = sr;
	}

	public L1StatReset getStatReset() {
		return _statReset;
	}

	public void cancelAbsoluteBarrier() { // �ƺ�Ҹ�Ʈ�ٸ����� ����
		if (this.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)) {
			this.getSkillEffectTimerSet()
					.killSkillEffectTimer(ABSOLUTE_BARRIER);
			// this.startHpRegeneration();
			// this.startMpRegeneration();
			this.startHpRegenerationByDoll();
			this.startMpRegenerationByDoll();
		}
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_�������)) {
			getSkillEffectTimerSet()
					.killSkillEffectTimer(L1SkillId.STATUS_�������);
		}
	}

	public int get_PKcount() {
		return _PKcount;
	}

	public void set_PKcount(int i) {
		_PKcount = i;
	}

	public int getClanid() {
		return _clanid;
	}

	public void setClanid(int i) {
		_clanid = i;
	}

	public String getClanname() {
		return clanname;
	}

	public void setClanname(String s) {
		clanname = s;
	}

	public L1Clan getClan() {
		return L1World.getInstance().getClan(getClanname());
	}

	public int getClanRank() {
		return _clanRank;
	}

	public void setClanRank(int i) {
		_clanRank = i;
	}

	public byte get_sex() {
		return _sex;
	}

	public void set_sex(int i) {
		_sex = (byte) i;
	}

	public int getAge() {
		return _age;
	}

	public void setAge(int i) {
		_age = i;
	}

	// ���� by ��ī

	private byte _hc = 0;

	public byte get_hc() {
		return _hc;
	}

	public void add_hc() {
		this._hc++;
	}

	public void radd_hc() {
		this._hc--;
	}

	private int huntCount;
	private int huntPrice;
	private String _reasontohunt;

	public String getReasonToHunt() {
		return _reasontohunt;
	}

	public void setReasonToHunt(String s) {
		_reasontohunt = s;
	}

	public int getHuntCount() {
		return huntCount;
	}

	public void setHuntCount(int i) {
		huntCount = i;
	}

	public int getHuntPrice() {
		return huntPrice;
	}

	public void setHuntPrice(int i) {
		huntPrice = i;
	}

	public boolean isGm() {
		return _gm;
	}

	public boolean isSGm() {
		return _Sgm;
	}

	public boolean is������() {
		int _����1�� = 101;
		int _����2�� = 102;
		int _����3�� = 103;
		int _����4�� = 104;
		int _����5�� = 105;
		int _����6�� = 106;
		int _����7�� = 107;
		int _����8�� = 108;
		int _����9�� = 109;
		int _����10�� = 110;

		if (getInventory().checkItem(60203)) {// ȯ�����������
			return true;
		}

		if (getMapId() == _����1�� && getInventory().checkItem(5001120)) {// 1��
			return true;
		}
		if (getMapId() == _����2�� && getInventory().checkItem(5001121)) {// 2��
			return true;
		}
		if (getMapId() == _����3�� && getInventory().checkItem(5001122)) {// 3��
			return true;
		}
		if (getMapId() == _����4�� && getInventory().checkItem(5001123)) {// 4��
			return true;
		}
		if (getMapId() == _����5�� && getInventory().checkItem(5001124)) {// 5��
			return true;
		}
		if (getMapId() == _����6�� && getInventory().checkItem(5001125)) {// 6��
			return true;
		}
		if (getMapId() == _����7�� && getInventory().checkItem(5001126)) {// 7��
			return true;
		}
		if (getMapId() == _����8�� && getInventory().checkItem(5001127)) {// 8��
			return true;
		}
		if (getMapId() == _����9�� && getInventory().checkItem(5001128)) {// 9��
			return true;
		}
		if (getMapId() == _����10�� && getInventory().checkItem(5001129)) {// 10��
			return true;
		}

		return false;
	}

	public void setSGm(boolean flag) {
		_Sgm = flag;
	}

	public void setGm(boolean flag) {
		_gm = flag;
	}

	/* ������� �巡������ */
	public boolean isThirdSpeed() {
		return (getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_DRAGONPERL) || get���ּӵ�() == 1);// ;;;;
	}

	private int _���ּӵ�; // �� ���� ���� 0.��� 1.ġ��ħ �̺�

	public int get���ּӵ�() {
		return _���ּӵ�;
	}

	public void set���ּӵ�(int i) {
		_���ּӵ� = i;
	}

	public int getPotionPlus() {
		return _PotionPlus + CalcStat.����ȸ������(getAbility().getTotalCon());

	}

	public void addPotionPlus(int i) {
		_PotionPlus += i;
	}

	public boolean isMonitor() {
		return _monitor;
	}

	public void setMonitor(boolean flag) {
		_monitor = flag;
	}



	public int getBowHitupByArmor() {
		return _bowHitupByArmor;
	}

	public void addBowHitupByArmor(int i) {
		_bowHitupByArmor += i;
	}

	public int getBowDmgupByArmor() {
		return _bowDmgupByArmor;
	}

	public void addBowDmgupByArmor(int i) {
		_bowDmgupByArmor += i;
	}

	public int getHitupByArmor() {
		return _HitupByArmor;
	}

	public void addHitupByArmor(int i) {
		_HitupByArmor += i;
	}

	public int getDmgupByArmor() {
		return _DmgupByArmor;
	}

	public void addDmgupByArmor(int i) {
		_DmgupByArmor += i;
	}

	public int getBowHitupByDoll() {
		return _bowHitupBydoll;
	}

	public void addBowHitupByDoll(int i) {
		_bowHitupBydoll += i;
	}

	public int getBowDmgupByDoll() {
		return _bowDmgupBydoll;
	}

	public void addBowDmgupByDoll(int i) {
		_bowDmgupBydoll += i;
	}

	private void setGresValid(boolean valid) {
		_gresValid = valid;
	}

	public boolean isGresValid() {
		return _gresValid;
	}

	public long getFishingTime() {
		return _fishingTime;
	}

	public void setFishingTime(long i) {
		_fishingTime = i;
	}

	public boolean isFishing() {
		return _isFishing;
	}

	public boolean isFishingReady() {
		return _isFishingReady;
	}

	public void setFishing(boolean flag) {
		_isFishing = flag;
	}

	public void setFishingReady(boolean flag) {
		_isFishingReady = flag;
	}

	private L1ItemInstance _fishingitem;

	public L1ItemInstance getFishingItem() {
		return _fishingitem;
	}

	public void setFishingItem(L1ItemInstance item) {
		_fishingitem = item;
	}

	public int fishX = 0;
	public int fishY = 0;

	public int getCookingId() {
		return _cookingId;
	}

	public void setCookingId(int i) {
		_cookingId = i;
	}

	public int getDessertId() {
		return _dessertId;
	}

	public void setDessertId(int i) {
		_dessertId = i;
	}

	public L1ExcludingList getExcludingList() {
		return _excludingList;
	}

	public L1ExcludingLetterList getExcludingLetterList() {
		return _excludingLetterList;
	}

	public AcceleratorChecker getAcceleratorChecker() {
		return _acceleratorChecker;
	}

	public int getTeleportX() {
		return _teleportX;
	}

	public void setTeleportX(int i) {
		_teleportX = i;
	}

	public int getTeleportY() {
		return _teleportY;
	}

	public void setTeleportY(int i) {
		_teleportY = i;
	}

	public short getTeleportMapId() {
		return _teleportMapId;
	}

	public void setTeleportMapId(short i) {
		_teleportMapId = i;
	}

	public int getTeleportHeading() {
		return _teleportHeading;
	}

	public void setTeleportHeading(int i) {
		_teleportHeading = i;
	}

	public int getTempCharGfxAtDead() {
		return _tempCharGfxAtDead;
	}

	public void setTempCharGfxAtDead(int i) {
		_tempCharGfxAtDead = i;
	}

	public boolean isCanWhisper() {
		return _isCanWhisper;
	}

	public void setCanWhisper(boolean flag) {
		_isCanWhisper = flag;
	}

	public boolean isShowTradeChat() {
		return _isShowTradeChat;
	}

	public void setShowTradeChat(boolean flag) {
		_isShowTradeChat = flag;
	}

	public boolean isShowWorldChat() {
		return _isShowWorldChat;
	}

	public void setShowWorldChat(boolean flag) {
		_isShowWorldChat = flag;
	}

	public int getFightId() {
		return _fightId;
	}

	public void setFightId(int i) {
		_fightId = i;
	}

	public boolean isPetRacing() {
		return petRacing;
	}

	public void setPetRacing(boolean Petrace) {
		this.petRacing = Petrace;
	}

	public int getPetRacingLAB() {
		return petRacingLAB;
	}

	public void setPetRacingLAB(int lab) {
		this.petRacingLAB = lab;
	}

	public int getPetRacingCheckPoint() {
		return petRacingCheckPoint;
	}

	public void setPetRacingCheckPoint(int p) {
		this.petRacingCheckPoint = p;
	}

	public void setHaunted(boolean i) {
		this.isHaunted = i;
	}

	public boolean isHaunted() {
		return isHaunted;
	}

	public void setDeathMatch(boolean i) {
		this.isDeathMatch = i;
	}

	public boolean isDeathMatch() {
		return isDeathMatch;
	}

	public int getCallClanId() {
		return _callClanId;
	}

	public void setCallClanId(int i) {
		_callClanId = i;
	}

	public int getCallClanHeading() {
		return _callClanHeading;
	}

	public void setCallClanHeading(int i) {
		_callClanHeading = i;
	}

	private boolean _isSummonMonster = false;

	public void setSummonMonster(boolean SummonMonster) {
		_isSummonMonster = SummonMonster;
	}

	public boolean isSummonMonster() {
		return _isSummonMonster;
	}

	private boolean _isShapeChange = false;

	public void setShapeChange(boolean isShapeChange) {
		_isShapeChange = isShapeChange;
	}

	public boolean isShapeChange() {
		return _isShapeChange;
	}

	private boolean _isArchShapeChange = false;

	public void setArchShapeChange(boolean isArchShapeChange) {
		_isArchShapeChange = isArchShapeChange;
	}

	public boolean isArchShapeChange() {
		return _isArchShapeChange;
	}

	private boolean _isArchPolyType = true; // t 1200 f -1

	public void setArchPolyType(boolean isArchPolyType) {
		_isArchPolyType = isArchPolyType;
	}

	public boolean isArchPolyType() {
		return _isArchPolyType;
	}

	public int getUbScore() {
		return _ubscore;
	}

	public void setUbScore(int i) {
		_ubscore = i;
	}

	// private int _girandungeon;
	/** ������� ����Ǿ� �ִ� �ð����� ���� �´�. ���� : 1�� */

	private int _timeCount = 0;

	public int getTimeCount() {
		return _timeCount;
	}

	public void setTimeCount(int i) {
		_timeCount = i;
	}

	private int _DeadTimeCount = 0;

	public int getDeadTimeCount() {
		return _DeadTimeCount;
	}

	public void setDeadTimeCount(int i) {
		_DeadTimeCount = i;
	}

	private int _tamtimecount = 0;

	public int gettamtimecount() {
		return _tamtimecount;
	}

	public void settamtimecount(int i) {
		_tamtimecount = i;
	}

	private int _dtimecount = 0;

	public int getdtimecount() {
		return _dtimecount;
	}

	public void setdtimecount(int i) {
		_dtimecount = i;
	}

	private int _����ī��Ʈ = 0;

	public int get����ī��Ʈ() {
		return _����ī��Ʈ;
	}

	public void set����ī��Ʈ(int i) {
		_����ī��Ʈ = i;
	}

	private boolean _isPointUser;

	/** ���� �ð��� ���� Pc ���� �Ǵ��Ѵ� */
	public boolean isPointUser() {
		return _isPointUser;
	}

	public void setPointUser(boolean i) {
		_isPointUser = i;
	}

	private long _limitPointTime;

	public long getLimitPointTime() {
		return _limitPointTime;
	}

	public void setLimitPointTime(long i) {
		_limitPointTime = i;
	}

	private int _safecount = 0;

	public int getSafeCount() {
		return _safecount;
	}

	public void setSafeCount(int i) {
		_safecount = i;
	}

	private Timestamp _logoutTime;

	public Timestamp getLogOutTime() {
		return _logoutTime;
	}

	public void setLogOutTime(Timestamp t) {
		_logoutTime = t;
	}

	public void setLogOutTime() {
		_logoutTime = new Timestamp(System.currentTimeMillis());
	}

	public Timestamp ����day;
	public int ����time;

	public Timestamp ����day;
	public int ����time;

	public Timestamp ���day;
	public int ���time;
	


	private int _ainhasad;

	public int getAinHasad() {
		// sendPackets(new S_SystemMessage(_ainhasad+" ����"));
		return _ainhasad;
		/*
		 * if(getNetConnection() == null || getNetConnection().getAccount() ==
		 * null) return 0; return getNetConnection().getAccount().ainhasad;
		 */
	}

	public void calAinHasad(int i) {
		/*
		 * if(getNetConnection() == null || getNetConnection().getAccount() ==
		 * null) return; int calc = getNetConnection().getAccount().ainhasad +
		 * i; if(calc <= 9999){//�����ϻ�� ���ʼҸ� üũ aincheck = true; } if (calc >=
		 * 5000000) calc = 5000000; getNetConnection().getAccount().ainhasad =
		 * calc;
		 */

		int calc = _ainhasad + i;
		if (calc >= 7000000)
			calc = 7000000;
		_ainhasad = calc;
	}

	public void setAinHasad(int i) {
		_ainhasad = i;
	}
	 private int[] beWanted = new int[6];
	public int[] getBeWanted() {
		return beWanted;
	}

	public void setBeWanted(int[] beWanted) {
		this.beWanted = beWanted;
	}
	
	public void initBeWanted() {
		addDmgup(-beWanted[0]);
		addHitup(-beWanted[1]);
		addBowDmgup(-beWanted[2]);
		addBowHitup(-beWanted[3]);
		getAbility().addSp(-beWanted[4]);
		addDamageReductionByArmor(-beWanted[5]);

		for (int i = 0; i < beWanted.length; i++) {
			beWanted[i] = 0;
sendPackets(new S_SPMR(this));
			sendPackets(new S_OwnCharAttrDef(this));
			sendPackets(new S_OwnCharStatus(this));
		}
	}
	
	public void addBeWanted() {
		addDmgup(beWanted[0]);
		addHitup(beWanted[1]);
		addBowDmgup(beWanted[2]);
		addBowHitup(beWanted[3]);
		getAbility().addSp(beWanted[4]);
		addDamageReductionByArmor(beWanted[5]);
sendPackets(new S_SPMR(this));
			sendPackets(new S_OwnCharAttrDef(this));
			sendPackets(new S_OwnCharStatus(this));
	}
	/** ��æƮ ���� ���� ó�� */
	private int _enchantitemid = 0;

	public int getLastEnchantItemid() {
		return _enchantitemid;
	}

	public void setLastEnchantItemid(int i, L1ItemInstance item) {
		if (getLastEnchantItemid() == i && i != 0) {
			sendPackets(new S_Disconnect());
			getInventory().removeItem(item, item.getCount());
			return;
		}
		_enchantitemid = i;
	}

	public void addPet(L1NpcInstance npc) {
		_petlist.put(npc.getId(), npc);
		sendPackets(new S_PetWindow((getPetList().size() + 1) * 3, npc, true));
		npc.setCurrentHp(npc.getCurrentHp());
	}

	/** ĳ���ͷκ���, pet, summon monster, tame monster, created zombie �� �����Ѵ�. */
	public void removePet(L1NpcInstance npc) {
		// Object petV[] = getPetList().values().toArray();
		int i = 0;
		for (L1NpcInstance petV : getPetList()) {
			if (petV.getId() == npc.getId()) {
				if (this.getNetConnection().CharReStart() == false) {
					sendPackets(new S_PetWindow(i * 3, npc, false));
				}
				// sendPackets(new S_SystemMessage("�갹��.."+petV.length));
				_petlist.remove(npc.getId());
			}
			i++;
		}
	}

	/** ĳ������ �ֿϵ��� ����Ʈ�� �����ش�. */
	public ArrayList<L1NpcInstance> getPetList() {
		ArrayList<L1NpcInstance> pet = new ArrayList<L1NpcInstance>();
		synchronized (_petlist) {
			pet.addAll(_petlist.values());
		}
		return pet;
	}

	public int getPetListSize() {
		synchronized (_petlist) {
			return _petlist.size();
		}
	}

	/** ĳ���Ϳ� doll�� �߰��Ѵ�. */
	public void addDoll(L1DollInstance doll) {
		_dolllist.put(doll.getId(), doll);
	}

	/** ĳ���ͷκ��� dool�� �����Ѵ�. */

	public void removeDoll(L1DollInstance doll) {
		synchronized (_dolllist) {
			_dolllist.remove(doll.getId());
		}
	}

	/** ĳ������ doll ����Ʈ�� �����ش�. */

	public ArrayList<L1DollInstance> getDollList() {
		ArrayList<L1DollInstance> doll = new ArrayList<L1DollInstance>();
		synchronized (_dolllist) {
			doll.addAll(_dolllist.values());
		}
		return doll;
	}

	public int getDollListSize() {
		synchronized (_dolllist) {
			return _dolllist.size();
		}
	}

	/** ĳ���Ϳ� �̺�Ʈ NPC(�ɸ��͸� ����ٴϴ�)�� �߰��Ѵ�. */
	public void addFollower(L1FollowerInstance follower) {
		_followerlist.put(follower.getId(), follower);
	}

	/** ĳ���ͷκ��� �̺�Ʈ NPC(�ɸ��͸� ����ٴϴ�)�� �����Ѵ�. */
	public void removeFollower(L1FollowerInstance follower) {
		_followerlist.remove(follower.getId());
	}

	/** ĳ������ �̺�Ʈ NPC(�ɸ��͸� ����ٴϴ�) ����Ʈ�� �����ش�. */
	public Map<Integer, L1FollowerInstance> getFollowerList() {
		return _followerlist;
	}

	public void ClearPlayerClanData(L1Clan clan) throws Exception {
		ClanWarehouse clanWarehouse = WarehouseManager.getInstance()
				.getClanWarehouse(clan.getClanName());
		if (clanWarehouse != null)
			clanWarehouse.unlock(getId());
		setClanid(0);
		setClanname("");
		setTitle("");
		setClanJoinDate(null);
		if (this != null) {
			S_CharTitle ct = new S_CharTitle(getId(), "");
			sendPackets(ct);
			Broadcaster.broadcastPacket(this, ct, true);
		}

		sendPackets(new S_�����ֽ�(getClan(), 2), true);
		sendPackets(new S_PacketBox(S_PacketBox.MSG_RANK_CHANGED, 0x0b,
				getName()), true);
		sendPackets(new S_ReturnedStat(this, S_ReturnedStat.CLAN_JOIN_LEAVE),
				true);
		Broadcaster.broadcastPacket(this, new S_ReturnedStat(this,
				S_ReturnedStat.CLAN_JOIN_LEAVE));
		sendPackets(new S_ClanJoinLeaveStatus(this), true);
		Broadcaster.broadcastPacket(this, new S_ClanJoinLeaveStatus(this));

		setClanRank(0);
		save();
	}

	private int _HpregenMax = 0;

	public int getHpregenMax() {
		return _HpregenMax;
	}

	public void setHpregenMax(int num) {
		this._HpregenMax = num;
	}

	private int _HpregenPoint = 0;

	public int getHpregenPoint() {
		return _HpregenPoint;
	}

	public void setHpregenPoint(int num) {
		this._HpregenPoint = num;
	}

	public void addHpregenPoint(int num) {
		this._HpregenPoint += num;
	}

	private int _HpcurPoint = 4;

	public int getHpcurPoint() {
		return _HpcurPoint;
	}

	 private int _msgType;




	    public int getMsgType() {

	        return _msgType;

	    }




	    public void setMsgType(int type) {

	        _msgType = type;

	    }
	public void setHpcurPoint(int num) {
		this._HpcurPoint = num;
	}

	private int _MpregenMax = 0;

	public int getMpregenMax() {
		return _MpregenMax;
	}

	public void setMpregenMax(int num) {
		this._MpregenMax = num;
	}

	private int _MpregenPoint = 0;

	public int getMpregenPoint() {
		return _MpregenPoint;
	}

	public void setMpregenPoint(int num) {
		this._MpregenPoint = num;
	}

	public void addMpregenPoint(int num) {
		this._MpregenPoint += num;
	}

	public final int �޽�_���� = 0;
	public final int �̵�_���� = 1;
	public final int ����_���� = 2;
	public int �÷��̾���� = 0;
	public long ���½ð� = 0;

	private int _MpcurPoint = 4;

	public int getMpcurPoint() {
		return _MpcurPoint;
	}

	public void setMpcurPoint(int num) {
		this._MpcurPoint = num;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////// ���ǵ��� ����
	// //////////////////////////////////////////////////////////

	private int hackTimer = -1;

	public int get_hackTimer() {
		return hackTimer;
	}

	public void increase_hackTimer() {
		// System.out.println(getName()+" ��üũ : "+hackTimer);
		if (hackTimer < 0)
			return;
		hackTimer++;
	}

	public void init_hackTimer() {
		hackTimer = 0;
		// ���ǵ� �� ���� �����忡 �߰�
	}

	private int _old_lawful;
	private int _old_exp;

	public int getold_lawful() {
		return this._old_lawful;
	}

	public void setold_lawful(int value) {
		this._old_lawful = value;
	}

	public int getold_exp() {
		return this._old_exp;
	}

	public void setold_exp(int value) {
		this._old_exp = value;
	}

	public void bkteleport() {
		int nx = getX();
		int ny = getY();
		int aaa = getMoveState().getHeading();
		switch (aaa) {
		case 1:
			nx += -1;
			ny += 1;
			break;
		case 2:
			nx += -1;
			ny += 0;
			break;
		case 3:
			nx += -1;
			ny += -1;
			break;
		case 4:
			nx += 0;
			ny += -1;
			break;
		case 5:
			nx += 1;
			ny += -1;
			break;
		case 6:
			nx += 1;
			ny += 0;
			break;
		case 7:
			nx += 1;
			ny += 1;
			break;
		case 0:
			nx += 0;
			ny += 1;
			break;
		default:
			break;
		}
		L1Teleport.teleport(this, nx, ny, getMapId(), aaa, false);
	}

	/**
	 * 2011.06.21 ������ ���� ������ �߰� (sjsjsj@nate.com)
	 */
	private Timestamp _MaanDelay = null;

	public Timestamp getMaanDelay() {
		return _MaanDelay;
	}

	public void setMaanDelay(Timestamp MaanDelay) {
		_MaanDelay = MaanDelay;
	}
	
	//����
	private Timestamp _����MaanDelay = null;

	public Timestamp get����MaanDelay() {
		return _����MaanDelay;
	}

	public void set����MaanDelay(Timestamp ����MaanDelay) {
		_����MaanDelay = ����MaanDelay;
	}
	
	//ǳ��
	private Timestamp _ǳ��MaanDelay = null;

	public Timestamp getǳ��MaanDelay() {
		return _ǳ��MaanDelay;
	}

	public void setǳ��MaanDelay(Timestamp ǳ��MaanDelay) {
		_ǳ��MaanDelay = ǳ��MaanDelay;
	}
	
	//����
	private Timestamp _����MaanDelay = null;

	public Timestamp get����MaanDelay() {
		return _����MaanDelay;
	}

	public void set����MaanDelay(Timestamp ����MaanDelay) {
		_����MaanDelay = ����MaanDelay;
	}
	
	//ȭ��
	private Timestamp _ȭ��MaanDelay = null;

	public Timestamp getȭ��MaanDelay() {
		return _ȭ��MaanDelay;
	}

	public void setȭ��MaanDelay(Timestamp ȭ��MaanDelay) {
		_ȭ��MaanDelay = ȭ��MaanDelay;
	}
	
	
	//����
	private Timestamp _����MaanDelay = null;

	public Timestamp get����MaanDelay() {
		return _����MaanDelay;
	}

	public void set����MaanDelay(Timestamp ����MaanDelay) {
		_����MaanDelay = ����MaanDelay;
	}
	
	//ź��
	private Timestamp _ź��MaanDelay = null;

	public Timestamp getź��MaanDelay() {
		return _ź��MaanDelay;
	}

	public void setź��MaanDelay(Timestamp ź��MaanDelay) {
		_ź��MaanDelay = ź��MaanDelay;
	}
	
	//����
	private Timestamp _����MaanDelay = null;

	public Timestamp get����MaanDelay() {
		return _����MaanDelay;
	}

	public void set����MaanDelay(Timestamp ����MaanDelay) {
		_����MaanDelay = ����MaanDelay;
	}
	
	
	private int _castleZoneTime = 0;

	public int getCastleZoneTime() {
		return _castleZoneTime;
	}

	public void setCastleZoneTime(int castleZoneTime) {
		this._castleZoneTime = castleZoneTime;
	}

	/** 2011.08.29 ������ �κ� �׼� ������ ���� */
	private int teleportTime = 0;
	private int currentTeleportCount = 0;
	private int skillTime = 0;
	private int currentSkillCount = 0;
	private int moveTime = 0;
	private int currentMoveCount = 0;

	public int getTeleportTime() {
		return teleportTime;
	}

	public void setTeleportTime(int teleportTime) {
		this.teleportTime = teleportTime;
	}

	public int getCurrentTeleportCount() {
		return currentTeleportCount;
	}

	public void setCurrentTeleportCount(int currentTeleportCount) {
		this.currentTeleportCount = currentTeleportCount;
	}

	public int getSkillTime() {
		return skillTime;
	}

	public void setSkillTime(int skillTime) {
		this.skillTime = skillTime;
	}

	public int getCurrentSkillCount() {
		return currentSkillCount;
	}

	public void setCurrentSkillCount(int currentSkillCount) {
		this.currentSkillCount = currentSkillCount;
	}

	public int getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(int moveTime) {
		this.moveTime = moveTime;
	}

	public int getCurrentMoveCount() {
		return currentMoveCount;
	}

	public void setCurrentMoveCount(int currentMoveCount) {
		this.currentMoveCount = currentMoveCount;
	}

	public int getinstance() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean zombie;

	private String _Memo = null;

	public String getMemo() {
		return _Memo;
	}

	public void setMemo(String i) {
		_Memo = i;
	}

	private int _Dg;

	public void addDg(int i) {
		_Dg += i;
		sendPackets(new S_PacketBox(S_PacketBox.UPDATE_DG, _Dg));
	}

	public int getDg() {
		return _Dg;
	}

	public boolean Sabutelok() {
		if (�ڴ��() || isTeleport() || isDead()
				|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�������)) {
			return false;
		}

		if (!isGm() && getMapId() == 6202 && ����) {
			sendPackets(new S_SystemMessage("�ڷ���Ʈ�� �ϱ� ���ؼ��� ����� ���ǰ� �ʿ� �մϴ�."));
			return false;
		}

		return true;
	}

	public boolean AttackCheckUseSKill = false;
	public int AttackCheckUseSKillDelay = 0;
	public boolean run = false;

	AutoAttack at = null;

	public void startatat() {
		synchronized (this) {
			if (at == null) {
				at = new AutoAttack();
				at.start();
			}
		}
	}

	class AutoAttack implements Runnable {
		public void start() {
			GeneralThreadPool.getInstance().execute(AutoAttack.this);
		}

		public void run() {
			try {
				attacking = true;
				if (!run) {
					at = null;
					target = null;
					attacking = false;
					return;
				}
				if (AttackCheckUseSKill) {
					GeneralThreadPool.getInstance().schedule(AutoAttack.this,
							AttackCheckUseSKillDelay);
					return;
				}

				autoattack();
				if (isGm()) {
					GeneralThreadPool.getInstance().schedule(AutoAttack.this,
							200);
				} else {
					GeneralThreadPool.getInstance().schedule(AutoAttack.this,
							calcSleepTime());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static final double Level_Rate_0 = 1.392;
	private static final double Level_Rate_15 = 1.321;
	private static final double Level_Rate_30 = 1.25;
	private static final double Level_Rate_45 = 1.178;
	private static final double Level_Rate_50 = 1.107;
	private static final double Level_Rate_52 = 1.035;
	private static final double Level_Rate_55 = 0.964;
	private static final double Level_Rate_75 = 0.892;
	private static final double Level_Rate_80 = 0.821;
	private static final double Level_Rate_82 = 0.812;
	private static final double Level_Rate_85 = 0.794;

	public static final double HASTE_RATE = 0.745;

	public static final double WAFFLE_RATE = 0.874;

	public static final double THIRDSPEED_RATE = 0.874;
	private Random _random = new Random(System.nanoTime());

	protected int calcSleepTime() {
		int gfxid = getGfxId().getTempCharGfx();
		int weapon = getCurrentWeapon();
		if (gfxid == 3784 || gfxid == 6137 || gfxid == 6142 || gfxid == 6147
				|| gfxid == 6152 || gfxid == 6157 || gfxid == 9205
				|| gfxid == 9206 || gfxid == 13152 || gfxid == 13153
				|| gfxid == 12702 || gfxid == 12681 || gfxid == 8812
				|| gfxid == 8817 || gfxid == 6267 || gfxid == 6270
				|| gfxid == 6273 || gfxid == 6276) {
			if (weapon == 24 && getWeapon() != null
					&& getWeapon().getItem().getType() == 18) {
				if (gfxid == 13152 || gfxid == 13153 || gfxid == 12702
						|| gfxid == 12681 || gfxid == 8812 || gfxid == 8817
						|| gfxid == 6267 || gfxid == 6270 || gfxid == 6273
						|| gfxid == 6276)
					weapon = 50;
				else
					weapon = 83;
			}
		}

		int interval = SprTable.getInstance().getAttackSpeed(gfxid, weapon + 1);
		if (interval == 0) {
			if (weapon + 1 == 1) {
				run = false;
				return 0;
			}

			if (!Config.spractionerr.contains(gfxid)) {
				Config.spractionerr.add(gfxid);
			//	System.out.println(this.getName() + "�� spr���� Ȯ�� ��� ���� : "
			//			+ gfxid + " Actid: " + (weapon + 1));
			}

			interval = 640;
		} else {
			if (gfxid == 13140) {
				interval *= Level_Rate_80;
			}
			if ((gfxid >= 11328 && gfxid <= 11448) || gfxid == 12237
					|| gfxid == 12702 || gfxid == 12681 || gfxid == 12541
					|| gfxid == 12542 || gfxid == 13152 || gfxid == 13153
					|| gfxid == 13389 || gfxid == 13388 || (gfxid >= 13715 && gfxid <= 13745)
					|| gfxid == 13631 || gfxid == 13635) {

				if (this.getLevel() >= 85) {
					interval *= Level_Rate_85;
				} else if (this.getLevel() >= 82) {
					interval *= Level_Rate_82;
				} else if (this.getLevel() >= 80) {
					interval *= Level_Rate_80;
				} else if (this.getLevel() >= 75) {
					interval *= Level_Rate_75;
				} else if (this.getLevel() >= 55) {
					interval *= Level_Rate_55;
				} else if (this.getLevel() >= 52) {
					interval *= Level_Rate_52;
				} else if (this.getLevel() >= 50) {
					interval *= Level_Rate_50;
				} else if (this.getLevel() >= 45) {
					interval *= Level_Rate_45;
				} else if (this.getLevel() >= 30) {
					interval *= Level_Rate_30;
				} else if (this.getLevel() >= 15) {
					interval *= Level_Rate_15;
				} else {
					interval *= Level_Rate_0;
				}
			}
		}
		if (this.isHaste()) {
			interval *= HASTE_RATE;
		}
		if (this.isBloodLust()) { // �����巯��Ʈ
			interval *= HASTE_RATE;
		}
		if (this.isBrave()) {
			interval *= HASTE_RATE;
		}
		if (this.isElfBrave()) {
			interval *= WAFFLE_RATE;
		}
		if (this.isThirdSpeed()) {
			interval *= THIRDSPEED_RATE;
		}
		if (this.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FIRE_BLESS)) {
			interval *= HASTE_RATE;
		}
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.WIND_SHACKLE)) {
			interval *= 2;
		}
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SLOW)) {
			interval *= 2;
		}

		int[] list = { 30, 40, 50 };
		interval += list[_random.nextInt(3)];
		// interval += _random.nextInt(21);
		return interval;
	}
	//�޺�
	public int getComboCount()
	  {
	    return this.comboCount;
	  }

	  public void setComboCount(int comboCount) {
	    this.comboCount = comboCount;
	  }
	  
	public L1Object target = null;
	private int range = 1;

	protected void autoattack() {
		boolean ��� = false;
		if (target == null) {
			run = false;
			return;
		}
		if (getNetConnection() == null) {
			run = false;
			return;
		}
		L1Object cktarget = L1World.getInstance().findObject(target.getId());
		if (cktarget == null) {
			run = false;
			return;
		}
		if (isGmInvis() || isGhost() || isDead() || isTeleport()
				|| isInvisDelay()) {
			run = false;
			return;
		}
		if (isInvisble()) {
			if(getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BLIND_HIDING)){
				if(!getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ؽ�)){			
					run = false;
					return;
				}else{
					getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.��ؽ�);
					��ؽ� = true;//
				}
			}else{
				run = false;
				return;
			}
			
		}
		if (isInvisDelay()) {
			run = false;
			return;
		}

		if (getSkillEffectTimerSet().hasSkillEffect(SHOCK_STUN)
				|| getSkillEffectTimerSet().hasSkillEffect(MOB_SHOCKSTUN_30)
				|| getSkillEffectTimerSet().hasSkillEffect(MOB_RANGESTUN_19)
				|| getSkillEffectTimerSet().hasSkillEffect(MOB_RANGESTUN_18)
				|| getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
				|| getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)
				|| getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
				|| getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				|| getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
				|| getSkillEffectTimerSet().hasSkillEffect(BONE_BREAK)
				|| getSkillEffectTimerSet().hasSkillEffect(PHANTASM)
				|| getSkillEffectTimerSet().hasSkillEffect(FOG_OF_SLEEPING)
				|| getSkillEffectTimerSet().hasSkillEffect(CURSE_PARALYZE)
				|| getSkillEffectTimerSet().hasSkillEffect(CURSE_PARALYZE2)
				|| getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.STATUS_CURSE_PARALYZED)
				|| getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.STATUS_POISON_PARALYZED)) {
			run = false;
			return;
		}
		if (getInventory() != null) {
			if (getInventory().calcWeightpercent() >= 83 && !getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_4)) {
				S_SystemMessage sm = new S_SystemMessage(
						"����ǰ�� �ʹ� ���ſ��� ������ �� �� �����ϴ�.");
				sendPackets(sm); // \f1�������� �ʹ� ���ſ� ������ ���� �����ϴ�.
				run = false;
				return;
			}
		}

		try {
			if (getNetConnection() != null) {
				if (GMCommands.autocheck_Tellist.size() > 0) {
					if (GMCommands.autocheck_Tellist
							.contains(getNetConnection().getAccountName())) {
						sendPackets(new S_SystemMessage(
								"�ڵ� ���� �亯�� 3ȸ�̻� Ʋ�� ���� ������ �־�� �մϴ�."));
						run = false;
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (getCurrentWeapon() == 20 || getCurrentWeapon() == 62) {
			range = 13;
		} else if (getCurrentWeapon() == 24) {
			range = 2;
			int polyId = getGfxId().getTempCharGfx();
			if (polyId == 3784 || polyId == 6137 || polyId == 6142
					|| polyId == 6147 || polyId == 6152 || polyId == 6157
					|| polyId == 9205 || polyId == 9206 || polyId == 13152
					|| polyId == 13153 || polyId == 12702 || polyId == 12681
					|| polyId == 8812 || polyId == 8817 || polyId == 6267
					|| polyId == 6270 || polyId == 6273 || polyId == 6276) {
				range = 1;
			}

			if (polyId == 7967 || polyId == 7968 || polyId == 10874
					|| polyId == 7846 || polyId == 7848 || polyId == 8719) {
				range = 1;
			}

			if (target instanceof L1MonsterInstance) {
				L1MonsterInstance mon = (L1MonsterInstance) target;
				if (mon.getNpcId() == 4038000 || mon.getNpcId() == 4200010
						|| mon.getNpcId() == 4200010
						|| mon.getNpcId() == 4039000
						|| mon.getNpcId() == 4039006
						|| mon.getNpcId() == 4039007
						|| mon.getNpcId() == 100014 || mon.getNpcId() == 100012
						|| mon.getNpcId() == 100235 || mon.getNpcId() == 45822
						|| mon.getNpcId() == 45262 || mon.getNpcId() == 81047
						|| mon.getNpcId() == 7000060 || mon.getNpcId() == 45673
						|| mon.getNpcId() == 45684 || mon.getNpcId() == 100851
						|| mon.getNpcId() == 100825 || mon.getNpcId() == 100858
						|| mon.getNpcId() == 45683 || mon.getNpcId() == 100815
						|| mon.getNpcId() == 100281 || mon.getNpcId() == 100034
						|| mon.getNpcId() == 45236 || mon.getNpcId() == 45252
						|| mon.getNpcId() == 81100 || mon.getNpcId() == 81228
						|| mon.getNpcId() == 45266 || mon.getNpcId() == 45356
						|| mon.getNpcId() == 45414 || mon.getNpcId() == 45841
						|| mon.getNpcId() == 45513 || mon.getNpcId() == 45581
						|| mon.getNpcId() == 7000051
						|| mon.getNpcId() == 100146 || mon.getNpcId() == 45547
						|| mon.getNpcId() == 45586 || mon.getNpcId() == 455470
						|| mon.getNpcId() == 7000052
						|| mon.getNpcId() == 100559)
					range = 2;
				else if (mon.getNpcId() == 100584 || mon.getNpcId() == 100588
						|| mon.getNpcId() == 100589)
					range = 4;
			}
		} else {
			range = 1;
			if (target instanceof L1MonsterInstance) {
				L1MonsterInstance mon = (L1MonsterInstance) target;
				if (mon.getNpcId() == 4038000 || mon.getNpcId() == 4200010
						|| mon.getNpcId() == 4200010
						|| mon.getNpcId() == 4039000
						|| mon.getNpcId() == 4039006
						|| mon.getNpcId() == 4039007
						|| mon.getNpcId() == 100014 || mon.getNpcId() == 100012
						|| mon.getNpcId() == 100235 || mon.getNpcId() == 45822
						|| mon.getNpcId() == 45262 || mon.getNpcId() == 81047
						|| mon.getNpcId() == 7000060 || mon.getNpcId() == 45673
						|| mon.getNpcId() == 45684 || mon.getNpcId() == 45683
						|| mon.getNpcId() == 100851 || mon.getNpcId() == 100825
						|| mon.getNpcId() == 100858 || mon.getNpcId() == 100815
						|| mon.getNpcId() == 100281 || mon.getNpcId() == 100034
						|| mon.getNpcId() == 45236 || mon.getNpcId() == 45252
						|| mon.getNpcId() == 81100 || mon.getNpcId() == 81228
						|| mon.getNpcId() == 45266 || mon.getNpcId() == 45356
						|| mon.getNpcId() == 45414 || mon.getNpcId() == 45841
						|| mon.getNpcId() == 45513 || mon.getNpcId() == 45581
						|| mon.getNpcId() == 7000051
						|| mon.getNpcId() == 100146 || mon.getNpcId() == 45547
						|| mon.getNpcId() == 45586 || mon.getNpcId() == 455470
						|| mon.getNpcId() == 7000052
						|| mon.getNpcId() == 100559)
					range = 2;
				else if (mon.getNpcId() == 100584 || mon.getNpcId() == 100588
						|| mon.getNpcId() == 100589)
					range = 3;
			}
		}

		if (getLocation().getTileLineDistance(
				new Point(target.getX(), target.getY())) > range) {
			run = false;
			return;
		}
		if (target instanceof L1Character) {
			if (((L1Character) target).isDead()) {
				run = false;
				return;
			}
		}

		if (target instanceof L1LittleBugInstance) {
			run = false;
			return;
		}
		if (target instanceof L1NpcInstance) {
			L1NpcInstance npc = (L1NpcInstance) target;
			if (npc.getNpcTemplate().get_npcId() == 90000
					|| npc.getNpcTemplate().get_npcId() == 90002
					|| npc.getNpcTemplate().get_npcId() == 90009
					|| npc.getNpcTemplate().get_npcId() == 90013
					|| npc.getNpcTemplate().get_npcId() == 90016) {// ���
				��� = true;
			}

			int hiddenStatus = ((L1NpcInstance) target).getHiddenStatus();
			if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK
					|| hiddenStatus == L1NpcInstance.HIDDEN_STATUS_FLY) {
				run = false;
				return;
			}
		}

		// ���� �׼��� ���� �� �ִ� ����� ó��
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ABSOLUTE_BARRIER)) { // �ƺ�Ҹ�Ʈ�ٸ�����
																					// ����
			getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.ABSOLUTE_BARRIER);
			startHpRegenerationByDoll();
			startMpRegenerationByDoll();
		}
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.MEDITATION)) {
			getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.MEDITATION);
		}

		�÷��̾���� = ����_����;
		���½ð� = System.currentTimeMillis() + 2000;

		delInvis();
		setRegenState(REGENSTATE_ATTACK);
		L1Character cha = null;
		if (target instanceof L1Character) {
			cha = (L1Character) target;
		}
		if (cha != null && target != null && !cha.isDead() && !���) {
			target.onAction(this);
		} else { // �ϴ� ����
			// TODO Ȱ�� ���鿡 �ϴ� �������� ���� ȭ���� ���� ������ �� �ȴ�
			if (���) {
				target.onAction(this);
			}
			int weaponId = 0;
			int weaponType = 0;
			L1ItemInstance weapon = getWeapon();
			L1ItemInstance arrow = null;
			L1ItemInstance sting = null;
			if (weapon != null) {
				weaponId = weapon.getItem().getItemId();
				weaponType = weapon.getItem().getType1();
				if (weaponType == 20) {
					arrow = getInventory().getArrow();
				}
				if (weaponType == 62) {
					sting = getInventory().getSting();
				}
			}
			getMoveState().setHeading(
					CharPosUtil.targetDirection(this, target.getX(),
							target.getY())); // ���⼼Ʈ
			if (weaponType == 20
					&& (weaponId == 190 || weaponId == 100190
							|| (weaponId >= 11011 && weaponId <= 11013) || arrow != null)) {
				if (arrow != null) {
					if (getGfxId().getTempCharGfx() == 7968
							|| getGfxId().getTempCharGfx() == 7967) {
						S_UseArrowSkill ua = new S_UseArrowSkill(this, 0, 7972,
								target.getX(), target.getY(), true);
						sendPackets(ua);
						Broadcaster.broadcastPacket(this, ua);
					} else if (getGfxId().getTempCharGfx() == 8842
							|| getGfxId().getTempCharGfx() == 8900) { // �����
						S_UseArrowSkill ua = new S_UseArrowSkill(this, 0, 8904,
								target.getX(), target.getY(), true);
						sendPackets(ua);
						Broadcaster.broadcastPacket(this, ua);
					} else if (getGfxId().getTempCharGfx() == 8845
							|| getGfxId().getTempCharGfx() == 8913) { // ������
						S_UseArrowSkill ua = new S_UseArrowSkill(this, 0, 8916,
								target.getX(), target.getY(), true);
						sendPackets(ua);
						Broadcaster.broadcastPacket(this, ua);
					} else {
						S_UseArrowSkill ua = new S_UseArrowSkill(this, 0, 66,
								target.getX(), target.getY(), true);
						sendPackets(ua);
						Broadcaster.broadcastPacket(this, ua);
					}
					getInventory().removeItem(arrow, 1);
				} else if (weaponId == 190 || weaponId == 100190) {
					S_UseArrowSkill ua = new S_UseArrowSkill(this, 0, 2349,
							target.getX(), target.getY(), true);
					sendPackets(ua);
					Broadcaster.broadcastPacket(this, ua);
					ua = null;
				} else if (weaponId >= 11011 && weaponId <= 11013) {
					S_UseArrowSkill ua = new S_UseArrowSkill(this, 0, 8771,
							target.getX(), target.getY(), true);
					sendPackets(ua);
					Broadcaster.broadcastPacket(this, ua);
				} // �߰�

			} else if (weaponType == 62 && sting != null) {
				if (getGfxId().getTempCharGfx() == 7968
						|| getGfxId().getTempCharGfx() == 7967) {
					S_UseArrowSkill ua = new S_UseArrowSkill(this, 0, 7972,
							target.getX(), target.getY(), true);
					sendPackets(ua);
					Broadcaster.broadcastPacket(this, ua);
				} else if (getGfxId().getTempCharGfx() == 8842
						|| this.getGfxId().getTempCharGfx() == 8900) { // �����
					S_UseArrowSkill ua = new S_UseArrowSkill(this, 0, 8904,
							target.getX(), target.getY(), true);
					sendPackets(ua);
					Broadcaster.broadcastPacket(this, ua);

				} else if (getGfxId().getTempCharGfx() == 8845
						|| this.getGfxId().getTempCharGfx() == 8913) { // ������
					S_UseArrowSkill ua = new S_UseArrowSkill(this, 0, 8916,
							target.getX(), target.getY(), true);
					sendPackets(ua);
					Broadcaster.broadcastPacket(this, ua);
					ua = null;
				} else {
					S_UseArrowSkill ua = new S_UseArrowSkill(this, 0, 2989,
							target.getX(), target.getY(), true);
					sendPackets(ua);
					Broadcaster.broadcastPacket(this, ua);
				}
				getInventory().removeItem(sting, 1);
			} else {
				// System.out.println("123");
				S_AttackPacket ap = new S_AttackPacket(this, 0,
						ActionCodes.ACTION_Attack);
				sendPackets(ap);
				Broadcaster.broadcastPacket(this, ap);
			}
			if (getWeapon() != null) {
				if (getWeapon().getItem().getType() == 17) {
					if (getWeapon().getItemId() == 410003) {
						S_SkillSound ss = new S_SkillSound(getId(), 6983);
						sendPackets(ss);
						Broadcaster.broadcastPacket(this, ss);
					} else {
						S_SkillSound ss = new S_SkillSound(getId(), 7049);
						sendPackets(ss);
						Broadcaster.broadcastPacket(this, ss);
					}
				}
			}
		}

	}

	public boolean LoadCheckStatus() {
		int totalS = getAbility().getAmount();
		int bonusS = getHighLevel() - 50;
		if (bonusS < 0) {
			bonusS = 0;
		}

		int calst = totalS - (bonusS + getAbility().getElixirCount() + 75);

		if (calst > 0 && !isGm()) {
			L1SkillUse l1skilluse = new L1SkillUse();
			l1skilluse.handleCommands(this, L1SkillId.CANCELLATION, getId(),getX(), getY(), null, 0, L1SkillUse.TYPE_LOGIN);
			if (getSecondWeapon() != null) {
				getInventory().setEquipped(getSecondWeapon(), false, false,false, true);
			}
			if (getWeapon() != null) {
				getInventory().setEquipped(getWeapon(), false, false, false,false);
			}

			sendPackets(new S_CharVisualUpdate(this));
			// sendPackets(new S_OwnCharStatus2(this));
			sendPackets(new S_OwnCharStatus(this));
			sendPackets(new S_PacketBox(S_PacketBox.char_ER, get_PlusEr()),
					true);

			for (L1ItemInstance armor : getInventory().getItems()) {
				for (int type = 0; type <= 12; type++) {
					if (armor != null) {
						getInventory().setEquipped(armor, false, false, false);
					}
				}
			}

			sendPackets(new S_SPMR(this));
			sendPackets(new S_OwnCharAttrDef(this));
			// sendPackets(new S_OwnCharStatus2(this));
			sendPackets(new S_OwnCharStatus(this));
			setReturnStat(getExp());
			setReturnStatus(1);
			sendPackets(new S_ReturnedStat(this, S_ReturnedStat.START));
			try {
				save();
			} catch (Exception e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}

			return false;
		}

		return true;
	}

	private ScheduledFuture<?> _teleLockCheck = null;

	public void setTeleLockCheck() {
		try {
			if (_teleLockCheck != null) {
				_teleLockCheck.cancel(true);
				_teleLockCheck = null;
			}
			_teleLockCheck = GeneralThreadPool.getInstance().schedule(
					new Runnable() {
						@Override
						public void run() {
							// TODO �ڵ� ������ �޼ҵ� ����
							try {
								if (getNetConnection() == null || isDead()
										|| isParalyzed())
									return;
								if (�ڴ��()) {
									if (L1World.getInstance().findObject(
											getId()) != null) {
										setTelType(10);
										new C_SabuTeleport(new byte[1],
												getNetConnection());
									}
								}/*
								 * else{ sendPackets(new
								 * S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK,
								 * false), true); }
								 */
							} catch (Exception e) {
							} finally {
								_teleLockCheck = null;
							}
						}
					}, 5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** �κ� ��Ʈ ���� **/

	 public void Delay(int delayTime) throws Exception{
		 
		int mdelayTime;
		mdelayTime =delayTime;
		Robot robot = new Robot();
		robot.delay(mdelayTime);
		}  //�κ����� ��Ʈ
	
	/** �κ� ��Ʈ ���� **/
	private int _elfAttrResetCount;
	public int getElfAttrResetCount() {
		return _elfAttrResetCount;
	}
	
	public void setElfAttrResetCount(int i) {
		_elfAttrResetCount = i;
	}

	public boolean attacking = false;

    /** ���� **/
    public boolean FouSlayer = false;
}